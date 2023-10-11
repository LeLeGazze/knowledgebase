package com.castle.fortress.admin.es;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.check.es.EsCheckLine;
import com.castle.fortress.admin.knowledge.dto.KbBasicDto;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbPropertyDesignDto;
import com.castle.fortress.admin.knowledge.dto.KbVideoDto;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.enums.FileTypeEnum;
import com.castle.fortress.admin.knowledge.enums.FromTypeEnum;
import com.castle.fortress.admin.knowledge.mapper.KbBasicMapper;
import com.castle.fortress.admin.knowledge.mapper.KbCategoryMapper;
import com.castle.fortress.admin.knowledge.service.*;
import com.castle.fortress.admin.knowledge.utis.FileUtil;
import com.castle.fortress.admin.system.dto.ConfigOssDto;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.mapper.SysUserMapper;
import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.admin.utils.FortressParseUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * es索引服务类
 */
@Service
@Slf4j
public class EsSearchService {
    @Autowired
    private ElasticsearchRestTemplate esTemplate;
    @Autowired
    private KbBasicMapper kbBasicMapper;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private KbCategoryMapper categoryMapper;

    @Autowired
    private KbModelService kbModelService;

    @Autowired
    private KbColConfigService kbColConfigService;

    @Autowired
    private KbModelDataService kbModelDataService;

    @Autowired
    private ConfigOssService configOssService;

    @Autowired
    private KbCategoryMapper kbCategoryMapper;

    @Autowired
    private IJcsegService iJcsegService;

    @Autowired
    private EsFileRepository esFileRepository;
    @Autowired
    private KbModelLabelService kbModelLabelService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 依据map对应的参数查询
     *
     * @param queryMap
     * @return
     */
    public SearchHits<EsFileDto> queryByMap(Map<String, String> queryMap) {
//        HashMap<String,Object> list = new HashMap<>();
        if (queryMap == null) {
            return null;
        }
        String type = null;
        //类型 00 标题 01 作者 02 内容 03 全部
        if (!CommonUtil.verifyParamEmpty(queryMap.get("type"))) {
            type = queryMap.get("type");
        }
        // 默认去查询知识  1 查询视频相关的
        String isVideo = "0";
        if (!CommonUtil.verifyParamEmpty(queryMap.get("isVideo"))) {
            isVideo = queryMap.get("isVideo");
        }
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder().fragmentSize(70)//高亮字段内容长度 默认100
                .numOfFragments(10)//高亮关键字数量 默认是5
                .preTags("<strong><font style='color:#006EFF'>").postTags("</font></strong>").field("fileName").field("fileContent").field("title");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("isVideo", isVideo));
        queryBuilder.must(QueryBuilders.matchQuery("isDelete", "2"));
        if ("00".equals(type) && StrUtil.isNotEmpty(queryMap.get("title"))) {
            queryBuilder.must(QueryBuilders.matchQuery("title", queryMap.get("title")));
        } else if ("01".equals(type) && StrUtil.isNotEmpty(queryMap.get("fileOwner"))) {
            queryBuilder.must(QueryBuilders.matchQuery("fileOwner", queryMap.get("fileOwner")));
        } else if ("02".equals(type) && StrUtil.isNotEmpty(queryMap.get("fileContent"))) {
            queryBuilder.must(QueryBuilders.matchQuery("fileContent", queryMap.get("fileContent")));
        } else if ("03".equals(type) && StrUtil.isNotEmpty(queryMap.get("keywords"))) {
            queryBuilder.must(QueryBuilders.multiMatchQuery(queryMap.get("keywords"), "title", "fileContent", "fileTags"));
        } else if ("04".equals(type) && StrUtil.isNotEmpty(queryMap.get("fileTags"))) {
            //文件标签过滤 支持多标签筛选，多标签的话要同时包含
            if (StrUtil.isNotEmpty(queryMap.get("fileTags"))) {
                String[] tags = queryMap.get("fileTags").split(",");
                for (String c : tags) {
                    queryBuilder.must(QueryBuilders.termQuery("fileTags", c));
                }
            }
        }
        //文件分类过滤 支持多分类筛选，多分类的话要同时包含
        if (StrUtil.isNotEmpty(queryMap.get("fileCategorys"))) {
            String[] category = queryMap.get("fileCategorys").split(",");
            for (String c : category) {
                queryBuilder.should(QueryBuilders.matchQuery("fileCategorys", c)).minimumShouldMatch(1);
            }
        }

        //文件类型过滤
        if (StrUtil.isNotEmpty(queryMap.get("fileType"))) {
            String fileType = queryMap.get("fileType");
            queryBuilder.must(QueryBuilders.termQuery("fileType", fileType));
        }
        //文件上传时间过滤
        if (StrUtil.isNotEmpty(queryMap.get("startTime")) && StrUtil.isNotEmpty(queryMap.get("endTime"))) {
            String startTime = queryMap.get("startTime");
            try {
                Date parse = format.parse(startTime);
                parse.setDate(parse.getDate() - 1);
                startTime = format.format(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            queryBuilder.must(QueryBuilders.rangeQuery("fileDate").format("yyyy-MM-dd HH:mm:ss").from(startTime).to(queryMap.get("endTime")));
        }
        //排序 上传时间倒叙
        Sort sort = Sort.by("fileDate").descending();
        Integer pageNum = 1;
        if (!StringUtils.isEmpty(queryMap.get("pageNum"))) {
            try {
                pageNum = Integer.valueOf(queryMap.get("pageNum"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                pageNum = 1;
            }
        }
        Integer pageSize = 10;
        if (!StringUtils.isEmpty(queryMap.get("pageSize"))) {
            try {
                pageSize = Integer.valueOf(queryMap.get("pageSize"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                pageSize = 10;
            }
        }

        query.withQuery(queryBuilder).withHighlightBuilder(highlightBuilder).withSort(SortBuilders.fieldSort("_score").order(SortOrder.DESC)).withPageable(PageRequest.of(pageNum - 1, pageSize));
        SearchHits<EsFileDto> searchHits = esTemplate.search(query.build(), EsFileDto.class);
//        long count = esTemplate.count(query.build(), EsFileDto.class);
//        System.out.println("count = " + count);
//        for (SearchHit<EsFileDto> d : searchHits) {
//            list.add(d);
//        }
//        list.put("searchCount",count);
//        list.put("searchData",searchHits);
        return searchHits;
    }

    public SearchHits<EsCheckLine> queryByList(String content) {
        HighlightBuilder highlightBuilder = new HighlightBuilder().fragmentSize(50)//高亮字段内容长度 默认100
                .numOfFragments(3);//高亮关键字数量 默认是5

        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("isVideo", "0"));
        queryBuilder.must(QueryBuilders.matchQuery("fileLine", content));
        query.withQuery(queryBuilder).withSort(SortBuilders.fieldSort("_score").order(SortOrder.DESC)).withHighlightBuilder(highlightBuilder).withPageable(PageRequest.of(0, 20));
        SearchHits<EsCheckLine> searchHits = esTemplate.search(query.build(), EsCheckLine.class);
        return searchHits;
    }

    /**
     * 异步获取数据往es 写入
     *
     * @param kbModelAcceptanceDto
     */
    @Async
    public void asyncSaveES(KbModelAcceptanceDto kbModelAcceptanceDto) {
        List<EsFileDto> fileDto = getEsFileDto(kbModelAcceptanceDto);
        saveCheckLine(fileDto); // 按行写入
        // 将数据插入到es中
        esTemplate.save(fileDto);
        StringBuilder builder = new StringBuilder();
        String id = fileDto.get(0).getId();

        for (EsFileDto esFileDto : fileDto) {
            builder.append(esFileDto.getFileContent());
        }
        updateByBasicWordCloud(id.split("-")[0], builder.toString());
        log.debug("文件入库es成功,id：{},fileName: {}", id.substring(0, id.length() - 1), fileDto.get(0).getFileName());
    }

    /**
     * 按行写入数据
     *
     * @param fileDto
     */
    public void saveCheckLine(List<EsFileDto> fileDto) {
        new Thread(() -> {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            List<EsCheckLine> checkLineList = ConvertUtil.transformObjList(fileDto, EsCheckLine.class);
            String filePathPrefix = configOssService.getFilePathPrefix();
            FortressParseUtil fortressParseUtil = new FortressParseUtil();
            Parser parser = new AutoDetectParser(fortressParseUtil.getConfig());
            for (EsCheckLine checkLine : checkLineList) {
                // 读取数据
                BodyContentHandler handler = new BodyContentHandler(-1);
                Metadata metadata = new Metadata();
                ParseContext pcontext = new ParseContext();
                FileInputStream inputstream = null;
                try {
                    if (checkLine.getFilePath().startsWith("http")){
                        inputstream= FileUtil.convertToFileInputStream(new URL(checkLine.getFilePath()).openStream());
                    }else {
                        inputstream = new FileInputStream(new File(filePathPrefix+checkLine.getFilePath()));
                    }
                    parser.parse(inputstream, handler, metadata, pcontext);
                    String content = "";
                    if (handler != null) {
                        content = handler.toString();
                    }
                    String[] split = content.split("\\n");
                    executorService.execute(() -> {
                        int count = 100;
                        ArrayList<EsCheckLine> esCheckLines = new ArrayList<>();
                        for (int i = 0; i < split.length; i++) {
                            String resStr = split[i];
                            if (StrUtil.isEmpty(resStr) || resStr.length() < 3) continue;
                            EsCheckLine newEsCheckLine = ConvertUtil.transformObj(checkLine, EsCheckLine.class);
                            newEsCheckLine.setFileLine(resStr.trim()).setId(checkLine.getId() + "$" + i);
                            esCheckLines.add(newEsCheckLine);
                            if (i % count == 0 && i != 0) {
                                esTemplate.save(esCheckLines);
                                log.debug("按行写入成功 size{} , fileName {}", esCheckLines.size(), esCheckLines.get(0).getFileName());
                                esCheckLines = new ArrayList<>();
                            }
                        }
                        if (esCheckLines.size() > 0) {
                            esTemplate.save(esCheckLines);
                            log.debug("按行写入成功 size{} , fileName {}", esCheckLines.size(), esCheckLines.get(0).getFileName());
                        }

                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    if (inputstream != null) {
                        try {
                            inputstream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }, "按行写入ES").start();
    }

    /**
     * 异步获取数据往es 更新
     *
     * @param kbModelAcceptanceDto
     */
    @Async
    public void asyncUpdateES(KbModelAcceptanceDto kbModelAcceptanceDto) {
        List<EsFileDto> fileDto = getEsFileDto(kbModelAcceptanceDto);
        if (fileDto == null || fileDto.size() == 0) {
            return;
        }
//        // 将数据插入到es中
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.prefixQuery("id", fileDto.get(0).getId().split("-")[0]));
        query.withQuery(boolQuery);
        esTemplate.delete(query.build(), EsFileDto.class);
        esTemplate.save(fileDto);
        updateCheckLine(fileDto);// 更新
        StringBuilder builder = new StringBuilder();
        String id = fileDto.get(0).getId();
        for (EsFileDto esFileDto : fileDto) {
            builder.append(esFileDto.getFileContent());
        }
        updateByBasicWordCloud(id.split("-")[0], builder.toString());
        log.debug("文件更新es成功,id：{},fileName: {}", id.substring(0, id.length() - 1), fileDto.get(0).getFileName());
    }

    public void updateCheckLine(List<EsFileDto> fileDto) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.prefixQuery("id", fileDto.get(0).getId().split("-")[0]));
        query.withQuery(boolQuery);
        esTemplate.delete(query.build(), EsCheckLine.class);
        saveCheckLine(fileDto);

    }

    public void updateByBasicWordCloud(String id, String fileContent) {
        try {
            ArrayList<HashMap<String, Object>> keywordsWeight = iJcsegService.getKeywordsWeight(new StringReader(fileContent));
            String word = JSONObject.toJSONString(keywordsWeight);
            KbBasicEntity kbBasicEntity = new KbBasicEntity();
            kbBasicEntity.setId(Long.parseLong(id));
            kbBasicEntity.setWordCloud(word);
            kbBasicMapper.updateById(kbBasicEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateByIdToIsDelete(String bid, String num) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.prefixQuery("id", bid.split("-")[0]));
        query.withQuery(boolQuery);
        SearchHits<EsFileDto> search = esTemplate.search(query.build(), EsFileDto.class);
        if (search.getSearchHits().size() == 0) {
            return;
        }
        for (SearchHit<EsFileDto> esFileDtoSearchHit : search) {
            EsFileDto content = esFileDtoSearchHit.getContent();
            if (content != null) {
                // 将数据插入到es中
                content.setIsDelete(num);
                esTemplate.delete(content);
                esTemplate.save(content);
                log.debug("文件入库esg更新成功,id：{},fileName: {}", content.getId(), content.getFileName());
            }
        }
    }

    public List<Object> updateByIdToLabel(HashMap<String, String> mapLabel, String[] ids) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        for (String id : ids) {
            boolQuery.should(QueryBuilders.prefixQuery("id", id.split("-")[0]));
        }
        boolQuery.minimumShouldMatch(1);
        query.withQuery(boolQuery);
        // 根据知识id查询es里面的数据
        SearchHits<EsFileDto> search = esTemplate.search(query.build(), EsFileDto.class);
        List<Object> requestOjbList = new ArrayList<>();
        if (search == null) {
            return requestOjbList;
        }
        ArrayList<EsFileDto> saveEsFileDtoList = new ArrayList<>();
        for (SearchHit<EsFileDto> searchHit : search) {
            EsFileDto content = searchHit.getContent();
            // 获取出标签删掉在插入进去
            if (content.getFileTags() != null && content.getFileTags().size() > 0) {
                content.setFileTags(content.getFileTags().stream().filter(item -> !item.equals(mapLabel.get(content.getId()))).collect(Collectors.toList()));
            }
            saveEsFileDtoList.add(content);
        }

        if (saveEsFileDtoList.size() > 0) {
            // 构建一个list 用来存储解运行后的结果
            ArrayList<KbBaseLabelTaskEntity> errorList = new ArrayList<>();
            ArrayList<Long> deleteList = new ArrayList<>();
            int processors = Runtime.getRuntime().availableProcessors();
            int nThreads = Math.min(saveEsFileDtoList.size(), processors);
            //创建一个线程池
            ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
            //使用的计数器
            CountDownLatch countDownLatch = new CountDownLatch(nThreads);
            // 循环将数据删除掉在写入进去
            for (EsFileDto esFileDto : saveEsFileDtoList) {
                executorService.execute(() -> {
                    try {
                        // 将数据插入到es中
                        esTemplate.delete(esFileDto);
                        esTemplate.save(esFileDto);
                        // 成功添加删除集合
                        deleteList.add(Long.parseLong(esFileDto.getId()));
                    } catch (RuntimeException e) {
                        KbBaseLabelTaskEntity kbBaseLabelTaskEntity = new KbBaseLabelTaskEntity();
                        kbBaseLabelTaskEntity.setId(Long.parseLong(esFileDto.getId()));
                        kbBaseLabelTaskEntity.setStatus(3);
                        kbBaseLabelTaskEntity.setTaskTime(new Date());
                        kbBaseLabelTaskEntity.setMessage(e.getMessage());
                        // 失败添加失败集合
                        errorList.add(kbBaseLabelTaskEntity);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            try {
                countDownLatch.await(30, TimeUnit.MINUTES);
                executorService.shutdown();
                // 更新数据库数据
                requestOjbList.add(deleteList);
                requestOjbList.add(errorList);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("文件入库esg更新成功,size：{},tag更新: {}", saveEsFileDtoList.size(), mapLabel.values());

        }
        return requestOjbList;
    }

    /**
     * 定时更新es 数据 浏览量 下载量
     */

    public void taskUpdateESBrowseDownload() {

    }


    /**
     * 异步获取数据往es 视频 更新
     *
     * @param kbVideoDto
     */
    @Async
    public void asyncUpdateESVideo(KbVideoDto kbVideoDto) {
        EsFileDto fileDto = getEsFileDtoVideo(kbVideoDto);
        // 将数据插入到es中
        esTemplate.delete(fileDto);
        esTemplate.save(fileDto);
        log.debug("文件入库es更新成功视频,id：{},fileName: {}", fileDto.getId(), fileDto.getFileName());
    }


    public List<EsFileDto> getEsFileDto(KbModelAcceptanceDto kbModelAcceptanceDto) {
        ArrayList<EsFileDto> esFileDtoArrayList = new ArrayList<>();
        EsFileDto fileDto = new EsFileDto();
        fileDto.setIsVideo("0");
        fileDto.setIsDelete("2");
        // 获取 basic表的主键
        KbBasicEntity byKbBasic = null;
        if (kbModelAcceptanceDto.getId() == null) {
            KbBasicDto kbBasicDto = ConvertUtil.transformObj(kbModelAcceptanceDto, KbBasicDto.class);
            byKbBasic = kbBasicMapper.findByKbBasic(kbBasicDto);
        } else {
            byKbBasic = kbBasicMapper.selectById(kbModelAcceptanceDto.getId());
        }
        fileDto.setId(String.valueOf(byKbBasic.getId()));
        //标题
        fileDto.setTitle(kbModelAcceptanceDto.getTitle());
        //  目录id
        Long swId = categoryMapper.selectById(byKbBasic.getCategoryId()).getSwId();
        fileDto.setSwId(String.valueOf(swId));
        // 文章内容
        HashMap<String, HashMap<String, String>> contentMap = null;
        if (!byKbBasic.getAttachment().isEmpty()) {
            Map<String, String> typeMap = Arrays.stream(FileTypeEnum.values()).collect(Collectors.toMap(FileTypeEnum::getName, va -> va.getDesc()));
            HashSet<String> fileTypeSet = new HashSet<>();
            List<JSONObject> jsonObjects = JSONObject.parseArray(byKbBasic.getAttachment(), JSONObject.class);

            List<Object> attachmentList = jsonObjects.stream().filter(iter -> {
                        boolean res = false;
                        if (typeMap.containsKey(iter.getString("url").substring(iter.getString("url").lastIndexOf(".") + 1))) {
                            fileTypeSet.add(typeMap.get(iter.getString("url").substring(iter.getString("url").lastIndexOf(".") + 1)));
                            res = true;
                        }
                        return res;
                    }
            ).collect(Collectors.toList());
            // TODO  校验附件中是否有附件存储，如何有读取文件路径 解析文件内容
            KbModelEntity modelEntity = kbModelService.getById(kbModelAcceptanceDto.getModelId());
            KbPropertyDesignDto kbPropertyDesignDto = new KbPropertyDesignDto();
            kbPropertyDesignDto.setModelId(kbModelAcceptanceDto.getModelId());

            List<KbPropertyDesignDto> kbPropertyDesignList = kbColConfigService.listKbColConfig(kbPropertyDesignDto);
            List<KbPropertyDesignDto> collect = kbPropertyDesignList.stream().filter(item -> item.getFormType().equals(FromTypeEnum.attachment.getCode())).collect(Collectors.toList());
            Map<String, Object> queryByDataId = kbModelDataService.queryByDataId(modelEntity.getCode(), byKbBasic.getId());
            ArrayList<Object> annexList = new ArrayList<>();
            collect.forEach(item -> {
                String url = String.valueOf(queryByDataId.get(item.getPropName()));
                List<JSONObject> fujianJson = JSONObject.parseArray(url, JSONObject.class);
                if (fujianJson != null && fujianJson.size() > 0) {
                    for (JSONObject itemJson : fujianJson) {
                        String s = itemJson.getString("url");
                        if (typeMap.containsKey(s.substring(s.lastIndexOf(".") + 1))) {
                            fileTypeSet.add(typeMap.get(s.substring(s.lastIndexOf(".") + 1)));
                            annexList.add(itemJson);
                        }
                    }
                }
            });
            attachmentList.addAll(annexList);
            log.info("上传文件总数：{}，基础文件需要提取文件数量：{} 扩展文件需要提取的数量：{}", jsonObjects.size(), attachmentList.size(), annexList.size());
            fileDto.setFileType(new ArrayList<>(fileTypeSet)); // 添加文件类型
//            fileDto.setFileContent(fileToStr(attachmentList)); // 解析文件内容
            contentMap = fileToMap(attachmentList);
            log.info("上传文件总数：全部提取成功");
        }
        //上传人员名称
        SysUser sysUser = userMapper.selectById(kbModelAcceptanceDto.getAuth());
        fileDto.setFileOwner(sysUser.getRealName());
        // 文件分类
        fileDto.setFileCategorys(String.valueOf(categoryMapper.selectById(kbModelAcceptanceDto.getCategoryId()).getId()));
        // 文件标签
        fileDto.setFileTags(kbModelAcceptanceDto.getLabel());
        // 录入时间
        fileDto.setFileDate(kbModelAcceptanceDto.getPubTime());
        // 来源
        String source = kbCategoryMapper.findBySource(kbModelAcceptanceDto.getCategoryId());
        fileDto.setSource(source);
        AtomicInteger index = new AtomicInteger();
        if (contentMap != null) {
            contentMap.forEach((k, v) -> {
                index.getAndIncrement();
                EsFileDto esFileDto = ConvertUtil.transformObj(fileDto, EsFileDto.class);
                esFileDto.setFilePath(k);
                esFileDto.setFileContent(v.get("content"));
                esFileDto.setFileName(v.get("fileName"));
                esFileDto.setLength(esFileDto.getFileContent().length());
                esFileDto.setId(fileDto.getId() + "-" + index.get());
                esFileDtoArrayList.add(esFileDto);
            });
        }
        return esFileDtoArrayList;
    }


    public String fileToStr(List<String> attachmentList) {
        if (attachmentList == null || attachmentList.size() == 0) {
            log.debug("提前文件的为空");
            return "";
        }
        StringBuffer strBuf = new StringBuffer();
        // 获取文件路径
        ConfigOssDto ftDto = new ConfigOssDto();
        ftDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigOssDto> ftDtoList = configOssService.selectBySelective(ftDto);
        ConfigOssDto configOssDto = ftDtoList.get(0);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(configOssDto.getPtConfig(), OssPlatFormDto.class);
        String filePositon = ossPlatFormDto.getLocalFilePosition();
        String fileUrl = ossPlatFormDto.getLocalFileUrl();//"http://localhost/image/";
        //创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(attachmentList.size());
        //使用的计数器
        CountDownLatch countDownLatch = new CountDownLatch(attachmentList.size());
        for (Object attachment : attachmentList) {
            // 将任务添加到队列中
            executorService.execute(() -> {
                try {
                    String fileName = attachment.toString().replace(fileUrl, filePositon + "/");
                    log.debug("正在提取name: {}", fileName);
                    FortressParseUtil fortressParseUtil = new FortressParseUtil();
                    strBuf.append(fortressParseUtil.parserFile(fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //计算器减去1
                    countDownLatch.countDown();
                }
            });
        }
        //阻塞,指定最大限制的等待时间，阻塞最多等待一定的时间后就解除阻塞
        try {
            countDownLatch.await(30, TimeUnit.MINUTES);
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return strBuf.toString().length() > 100000 ? strBuf.toString().substring(0, 99998) : strBuf.toString();
    }

    public HashMap<String, HashMap<String, String>> fileToMap(List<Object> attachmentList) {
        if (attachmentList == null || attachmentList.size() == 0) {
            log.debug("提前文件的为空");
            return null;
        }
        HashMap<String, HashMap<String, String>> resMap = new HashMap<String, HashMap<String, String>>();
        // 获取文件路径
        ConfigOssDto ftDto = new ConfigOssDto();
        ftDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigOssDto> ftDtoList = configOssService.selectBySelective(ftDto);
        ConfigOssDto configOssDto = ftDtoList.get(0);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(configOssDto.getPtConfig(), OssPlatFormDto.class);
        String filePositon = ossPlatFormDto.getLocalFilePosition();
        String fileUrl = ossPlatFormDto.getLocalFileUrl();//"http://localhost/image/";
        //创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(attachmentList.size());
        //使用的计数器
        CountDownLatch countDownLatch = new CountDownLatch(attachmentList.size());
        for (Object attachment : attachmentList) {
            // 将任务添加到队列中
            executorService.execute(() -> {
                try {
                    JSONObject jsonObject = JSONObject.parseObject(attachment.toString());
                    String url = jsonObject.getString("url");
//                    String fileName = url.replace(fileUrl, filePositon + "/");
                    log.debug("正在提取name: {}", url);
                    FortressParseUtil fortressParseUtil = new FortressParseUtil();
                    HashMap<String, String> map = new HashMap<>();
                    String s = fortressParseUtil.parserFile(url);
                    map.put("content", s.length() > 999999 ? s.substring(0, 999999) : s);
                    map.put("fileName", jsonObject.getString("name"));
                    resMap.put(url.replace(fileUrl, ""), map);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //计算器减去1
                    countDownLatch.countDown();
                }
            });
        }
        //阻塞,指定最大限制的等待时间，阻塞最多等待一定的时间后就解除阻塞
        try {
            countDownLatch.await(30, TimeUnit.MINUTES);
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resMap;
    }

    public int deleteByid(Long id) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.prefixQuery("id", String.valueOf(id)));
        query.withQuery(boolQuery);
        ByQueryResponse delete = esTemplate.delete(query.build(), EsCheckLine.class);
        esTemplate.delete(query.build(), EsFileDto.class);
        log.debug("es delete id : {} res: {}", id, delete);
        return 1;
    }

    public int deleteByBatchId(List<String> ids) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.termsQuery("id", ids));
        query.withQuery(boolQuery);
        esTemplate.delete(query.build(), EsFileDto.class);
        return 0;
    }

    @Async
    public void saveEsFileVideo(KbVideoDto kbVideoDto) {
        EsFileDto esFileDtoVideo = getEsFileDtoVideo(kbVideoDto);
        // 将数据插入到es中
        esTemplate.save(esFileDtoVideo);
        log.debug("文件入库es成功 视频,id：{},title: {}", esFileDtoVideo.getId(), esFileDtoVideo.getFileName());
    }

    public EsFileDto getEsFileDtoVideo(KbVideoDto kbVideoDto) {
        EsFileDto fileDto = new EsFileDto();
        fileDto.setIsVideo("1");
        fileDto.setIsDelete("2");
        //视频id
        fileDto.setId(String.valueOf(kbVideoDto.getId()));
        // 标题
        fileDto.setFileName(kbVideoDto.getTitle());
        // 视频备注
        fileDto.setFileContent(kbVideoDto.getRemark());
        //上传人员名称
        SysUser sysUser = userMapper.selectById(kbVideoDto.getAuth());
        fileDto.setFileOwner(sysUser.getRealName());
        // 文件分类
        fileDto.setFileCategorys(String.valueOf(categoryMapper.selectById(kbVideoDto.getCategoryId()).getId()));
        // 文件标签
        fileDto.setFileTags(kbVideoDto.getLabel());
        // 录入时间
        fileDto.setFileDate(kbVideoDto.getPubTime());
        //  目录id
        Long swId = categoryMapper.selectById(kbVideoDto.getCategoryId()).getSwId();
        fileDto.setSwId(String.valueOf(swId));
        // 来源
        fileDto.setSource(kbCategoryMapper.findBySource(kbVideoDto.getCategoryId()));
        return fileDto;
    }

    public void initData() {
        boolean res = !esFileRepository.findAll().iterator().hasNext();
        if (res) {
            log.debug("初始化基础数据·········");
            List<KbBasicEntity> kbBasicEntities = kbBasicMapper.selectList(new QueryWrapper<>());
            for (KbBasicEntity kbBasicEntity : kbBasicEntities) {
                KbModelAcceptanceDto kbModelAcceptanceDto = ConvertUtil.transformObj(kbBasicEntity, KbModelAcceptanceDto.class);
                kbModelAcceptanceDto.setAttachments(JSONArray.parseArray(kbBasicEntity.getAttachment()));
                List<KbModelLabelEntity>  labelEntities=kbModelLabelService.findByBId(kbBasicEntity.getId());
                kbModelAcceptanceDto.setLabel(labelEntities.stream().map(KbModelLabelEntity::getName).collect(Collectors.toList()));
                asyncSaveES(kbModelAcceptanceDto);
            }
        }
    }
}
