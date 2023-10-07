package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelDto;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
import com.castle.fortress.admin.knowledge.mapper.KbModelMapper;
import com.castle.fortress.admin.knowledge.service.KbModelService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * cms模型配置表 服务实现类
 *
 * @author castle
 * @since 2022-03-02
 */
@Service
public class KbModelServiceImpl extends ServiceImpl<KbModelMapper, KbModelEntity> implements KbModelService {
    @Autowired
    private KbModelMapper kbModelMapper;
    /**
    * 获取查询条件
    * @param cmsModelDto
    * @return
    */
    public QueryWrapper<KbModelEntity> getWrapper(KbModelDto cmsModelDto){
        QueryWrapper<KbModelEntity> wrapper= new QueryWrapper();
        if(cmsModelDto != null){
            KbModelEntity kbModelEntity = ConvertUtil.transformObj(cmsModelDto,KbModelEntity.class);
            wrapper.like(kbModelEntity.getId() != null,"id",kbModelEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbModelEntity.getName()),"name",kbModelEntity.getName());
            wrapper.like(StrUtil.isNotEmpty(kbModelEntity.getCode()),"code",kbModelEntity.getCode());
            wrapper.like(kbModelEntity.getStatus() != null,"status",kbModelEntity.getStatus());
            wrapper.eq(kbModelEntity.getIsDeleted()!=null,"is_deleted",kbModelEntity.getIsDeleted());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<KbModelDto> pageCmsModel(Page<KbModelDto> page, KbModelDto kbModelDto) {
        QueryWrapper<KbModelEntity> wrapper = getWrapper(kbModelDto);
        Page<KbModelEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbModelEntity> kbModelPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<KbModelDto> pageDto = new Page(kbModelPage.getCurrent(), kbModelPage.getSize(),kbModelPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbModelPage.getRecords(),KbModelDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbModelDto> pageCmsModelExtends(Page<KbModelDto> page, KbModelDto kbModelDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        KbModelEntity kbModelEntity = ConvertUtil.transformObj(kbModelDto,KbModelEntity.class);
        List<KbModelEntity> cmsModelList=baseMapper.extendsList(pageMap,kbModelEntity);
        Long total = baseMapper.extendsCount(kbModelEntity);
        Page<KbModelDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(cmsModelList,KbModelDto.class));
        return pageDto;
    }
    @Override
    public KbModelDto getByIdExtends(Long id){
        KbModelEntity  kbModelEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbModelEntity,KbModelDto.class);
    }

    @Override
    public List<KbModelDto> listCmsModel(KbModelDto kbModelDto){
        kbModelDto.setIsDeleted(2);
        QueryWrapper<KbModelEntity> wrapper = getWrapper(kbModelDto);
        wrapper.eq("status","1");
        List<KbModelEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbModelDto.class);
    }

    @Override
    public boolean dropTable(String tbName) {
        return false;
    }

    @Override
    public RespBody checkColumnRepeat(KbModelAcceptanceDto kbModelAcceptanceDto) {

        List<KbPropertyDesignEntity> list = kbModelAcceptanceDto.getPropCols();
        List<KbPropertyDesignEntity> cols = kbModelAcceptanceDto.getPropCols();
        if(list != null && !list.isEmpty()){
            //新增
            if(kbModelAcceptanceDto.getModelId() == null){
                return RespBody.fail(BizErrorCode.CMS_CODE_EXIST_ERROR);
                //修改
            }else{
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if(i == j){
                            continue;
                        }
                        if(list.get(i).getPropName().equals(cols.get(j).getPropName())) {
                            return RespBody.fail("字段重复，请检查字段后重试");
                        }

                    }
                }
            }
        }
        return RespBody.data("校验通过");
    }

    @Override
    public KbModelDto getByArticleId(Long id) {
        return ConvertUtil.transformObj(baseMapper.getByArticleId(id),KbModelDto.class);
    }

    @Override
    public KbModelDto getByCode(String code) {
        return ConvertUtil.transformObj(baseMapper.getByCode(code),KbModelDto.class);
    }

    @Override
    public List<Long> findswId(Long id) {
        return kbModelMapper.findswId(id);
    }
}

