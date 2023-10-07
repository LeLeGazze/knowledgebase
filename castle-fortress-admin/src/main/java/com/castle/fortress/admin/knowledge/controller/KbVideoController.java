package com.castle.fortress.admin.knowledge.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.dto.KbVideoShowDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicUserEntity;
import com.castle.fortress.admin.knowledge.entity.KbVideoEntity;
import com.castle.fortress.admin.knowledge.dto.KbVideoDto;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.mapper.KbWarehouseAuthMapper;
import com.castle.fortress.admin.knowledge.service.KbBasicService;
import com.castle.fortress.admin.knowledge.service.KbBasicUserService;
import com.castle.fortress.admin.knowledge.service.KbVideoService;
import com.castle.fortress.admin.knowledge.service.KbWarehouseAuthService;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.classgraph.json.Id;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.castle.fortress.common.entity.DynamicExcelEntity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 视频库 控制器
 *
 * @author
 * @since 2023-05-13
 */
@Api(tags = "视频库管理控制器")
@Controller
@Slf4j
public class KbVideoController {
    @Autowired
    private KbVideoService kbVideoService;

    @Autowired
    private KbBasicUserService kbBasicUserService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private KbWarehouseAuthMapper kbWarehouseAuthMapper;
    @Autowired
    private KbWarehouseAuthService kbWarehouseAuthService;

    /**
     * 视频库的分页展示
     *
     * @param kbVideoShowDto 视频库实体类
     * @param current        当前页
     * @param size           每页记录数
     * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("视频库分页展示")
    @GetMapping("/knowledge/kbVideo/page")
    @ResponseBody
//    @RequiresPermissions("knowledge:kbVideo:pageList")
    public RespBody<IPage<KbVideoShowDto>> pageKbVideo(KbVideoShowDto kbVideoShowDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbVideoDto> page = new Page(pageIndex, pageSize);
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        if (kbVideoShowDto == null || kbVideoShowDto.getSwId() == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {

            IPage<KbVideoShowDto> pages = kbVideoService.pageKbVideoAdmin(page, kbVideoShowDto);
            return RespBody.data(pages);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUidVideo(uid, "00");
            if (uAuths == null || uAuths.size() == 0) {
                Page<KbVideoShowDto> tmpPage = new Page(pageIndex, pageSize);
                tmpPage.setRecords(new ArrayList<>());
                return RespBody.data(tmpPage);
            }
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            IPage<KbVideoShowDto> pages = kbVideoService.pageKbVideo(page, kbVideoShowDto, uid, integers);
            return RespBody.data(pages);
        }
    }


    /**
     * 查询视频详情
     *
     * @param id  视频主键
     * @param num 插入1 代表详情 不插入代表编辑
     * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查询视频详情")
    @GetMapping("/knowledge/kbVideo/findByIdVideo")
    @ResponseBody
    public RespBody<KbVideoDto> findByIdVideo(@RequestParam Long id, @RequestParam(required = false) Integer num) {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 参数id 不允许为空
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            KbVideoEntity byId = kbVideoService.getById(id);
            if (byId == null) {
                throw new BizException("该视频已经被删除或不存在");
            }
            KbVideoDto pages = kbVideoService.findByIdVideo(id);
            pages.setVideoSrc(JSONObject.parseArray(String.valueOf(byId.getVideoSrc())));
            pages.setUpdateAuthority(true); //管理员默认添加update允许
            pages.setDeleteCommentsAuthority(true); // 校验是否有删除评论权限
            if (byId.getStatus() == 1) {
                savaKbBasic(num, id, uid);
                pages.setReadCount(pages.getReadCount()+1);
            }
            return RespBody.data(pages);
        } else {
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            // 校验用户是否有查看该知识权限
            KbVideoEntity allByBasic = kbVideoService.findByIdAuth(uid, integers, id);
            KbVideoEntity byId = kbVideoService.getById(id);
            if (byId == null) {
                throw new BizException("该视频已经被删除或不存在");
            }
            if (allByBasic == null  && !byId.getAuth().equals(uid)) {
                throw new BizException("暂无权限，请联系管理员授权");
            }
            if (byId.getStatus() == 1) {
                savaKbBasic(num, id, uid);
            }
            KbVideoDto kbVideoDto = kbVideoService.findByIdVideo(id);
            kbVideoDto.setVideoSrc(JSONObject.parseArray(String.valueOf(byId.getVideoSrc())));
            // 校验是否有编辑 权限
            Callable<KbVideoEntity> updateCallable = new Callable<KbVideoEntity>() {
                @Override
                public KbVideoEntity call() throws Exception {
                    List<Integer> integers = Arrays.asList(
                            KbAuthEnum.UPDATE.getCode(),
                            KbAuthEnum.MANAGE.getCode()
                    );
                    return kbVideoService.findByIdAuth(uid, integers, id);
                }
            };
            // 校验是否有删除评论权限
            Callable<KbVideoEntity> deleteCallable = new Callable<KbVideoEntity>() {
                @Override
                public KbVideoEntity call() throws Exception {
                    List<Integer> integers = Arrays.asList(KbAuthEnum.MANAGE.getCode());
                    return kbVideoService.findByIdAuth(uid, integers, id);
                }
            };
            FutureTask<KbVideoEntity> updateFuture = new FutureTask<>(updateCallable);
            FutureTask<KbVideoEntity> deletedFuture = new FutureTask<>(deleteCallable);
            new Thread(updateFuture).start();
            new Thread(deletedFuture).start();
            try {
                kbVideoDto.setUpdateAuthority(updateFuture.get() != null); //校验是否有编辑权限
                kbVideoDto.setDeleteCommentsAuthority(deletedFuture.get() != null); // 校验是否有删除评论权限
            } catch (Exception e) {
                e.printStackTrace();
            }
            return RespBody.data(kbVideoDto);
        }
    }

    private void savaKbBasic(Integer num, Long id, Long uid) {
        new Runnable() {
            @Override
            public void run() {
                if (num != null && num == 1) {
                    KbBasicUserEntity kbBasicUserEntity = new KbBasicUserEntity();
                    kbBasicUserEntity.setBId(id);
                    kbBasicUserEntity.setUserId(uid);
                    kbBasicUserEntity.setType(1);
                    kbBasicUserEntity.setStatus(2);
                    kbBasicUserService.save(kbBasicUserEntity);
                }
            }
        }.run();

    }

    /**
     * 视频库的列表展示
     *
     * @param kbVideoDto 视频库实体类
     * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("视频库列表展示")
    @GetMapping("/knowledge/kbVideo/list")
    @ResponseBody
    public RespBody<List<KbVideoDto>> listKbVideo(KbVideoDto kbVideoDto) {
        List<KbVideoDto> list = kbVideoService.listKbVideo(kbVideoDto);
        return RespBody.data(list);
    }

    /**
     * 视频库保存
     *
     * @param kbVideoDto 视频库实体类
     * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.INSERT)
    @ApiOperation("视频库保存")
    @PostMapping("/knowledge/kbVideo/save")
    @ResponseBody
//    @RequiresPermissions("knowledge:kbVideo:save")
    public RespBody<String> saveKbVideo(@RequestBody KbVideoDto kbVideoDto) {
        if (kbVideoDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbVideoService.saveAll(kbVideoDto)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 视频库编辑
     *
     * @param kbVideoDto 视频库实体类
     * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("视频库编辑")
    @PostMapping("/knowledge/kbVideo/edit")
    @ResponseBody
//    @RequiresPermissions("knowledge:kbVideo:edit")
    public RespBody<String> updateKbVideo(@RequestBody KbVideoDto kbVideoDto) {
        if (kbVideoDto == null || kbVideoDto.getId() == null || kbVideoDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbVideoService.editById(kbVideoDto)) {
            return RespBody.data("修改成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 视频库删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.DELETE)
    @ApiOperation("视频库删除")
    @PostMapping("/knowledge/kbVideo/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbVideo:delete")
    public RespBody<String> deleteKbVideo(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbVideoService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 视频库批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.DELETE)
    @ApiOperation("视频库批量删除")
    @PostMapping("/knowledge/kbVideo/deleteBatch")
    @ResponseBody
//    @RequiresPermissions("knowledge:kbVideo:deleteBatch")
    public RespBody<String> deleteKbVideoBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            //管理员可以删除所有知识
            int deleteByIds = kbVideoService.deleteByIdsAdmin(ids);
            return RespBody.data(deleteByIds > 0 ? "删除成功" : "删除失败");
        } else {
            //只有管理员才有权限
            List<Integer> integers = Arrays.asList(KbAuthEnum.MANAGE.getCode());
            List<Long> kbWarehouseAuthEntityList = kbWarehouseAuthMapper.findByUidCategory(uid, integers);
            String res = kbVideoService.deleteByIds(kbWarehouseAuthEntityList, ids);
            if (res.contains("无权限删除该分类下的内容")) {
                return RespBody.fail(res);
            }
            return RespBody.data(res);
        }
    }

    /**
     * 视频库详情
     *
     * @param id 视频库id
     * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("视频库详情")
    @GetMapping("/knowledge/kbVideo/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbVideo:info")

    public RespBody<KbVideoDto> infoKbVideo(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbVideoDto kbVideoDto = kbVideoService.getByIdExtends(id);

        return RespBody.data(kbVideoDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbVideo/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbVideoDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbVideoDto> list = kbVideoService.listKbVideo(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }

    @GetMapping("/knowledge/kbVideo/{vid}")
    @ResponseBody
    public void play(@PathVariable("vid") String vid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        String filePath = null;
        // 去redis 获取数据视频存储的URL
        filePath = (String) redisUtils.get("video:filePath:" + vid);
        // 查询数据库获取出要读取的视频本地地址
        if (filePath == null || filePath.isEmpty()) {
            filePath = kbVideoService.findByVidToUrl(vid);
            //存储redis 有效一天
            redisUtils.set("video:filePath:" + vid, filePath, 1L, TimeUnit.DAYS);
        }

        File file = new File(filePath);
        long fileLength = file.length();
        // 随机读文件
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

        //获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");
        long range = 0;
        if (StrUtil.isNotBlank(rangeString)) {
            range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
        }
        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        //设置内容类型
        response.setHeader("Content-Type", "video/mp4");
        //返回码需要为206，代表只处理了部分请求，响应了部分数据
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        // 移动访问指针到指定位置
        randomAccessFile.seek(range);
        // 每次请求只返回1MB的视频流
        byte[] bytes = new byte[1024 * 1024 * 2];
        int len = randomAccessFile.read(bytes);
        //设置此次相应返回的数据长度
        response.setContentLength(len);
        //设置此次相应返回的数据范围
        response.setHeader("Content-Range", "bytes " + range + "-" + (fileLength - 1) + "/" + fileLength);
        // 将这1MB的视频流响应给客户端
        try {
            outputStream.write(bytes, 0, len);
        } catch (IOException e) {
            log.debug("同一个设备已经建立了视频连接：视频id【{}】 主机名称：【{}】", vid, request.getLocalName());
        }
        outputStream.close();
        randomAccessFile.close();

//        System.out.println("返回数据区间:【" + range + "-" + (range + len) + "】");
    }

    /**
     * 视频首页列表展示、
     * swId 视频目录id
     * <p>
     * * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("视频首页列表展示")
    @GetMapping("/knowledge/kbVideo/showListVideo")
    @ResponseBody

    public RespBody<List<KbVideoDto>> showListVideo(@RequestParam(required = false) Long swId) {

        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {

            List<KbVideoDto> videoDtoList = kbVideoService.showListVideoAdmin(swId);
            return RespBody.data(videoDtoList);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUidVideo(uid, "00");
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(new ArrayList<>());
            }
            //只有管理员才有权限
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.MANAGE.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.UPDATE.getCode()
            );
            List<KbVideoDto> videoDtoList = kbVideoService.showListVideo(swId, integers, uid);
            return RespBody.data(videoDtoList);
        }
    }

    /**
     * 视频推荐
     * swId 视频目录id
     * <p>
     * * @return
     */
    @CastleLog(operLocation = "视频库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("视频推荐")
    @GetMapping("/knowledge/kbVideo/randVideoList")
    @ResponseBody

    public RespBody<List<KbVideoDto>> randVideoList() {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {

            List<KbVideoDto> videoDtoList = kbVideoService.randVideoListAdmin();
            return RespBody.data(videoDtoList);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUidVideo(uid, "00");
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(new ArrayList<>());
            }
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.MANAGE.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.UPDATE.getCode()
            );
            List<KbVideoDto> videoDtoList = kbVideoService.randVideoList(integers, uid);
            return RespBody.data(videoDtoList);
        }
    }


}
