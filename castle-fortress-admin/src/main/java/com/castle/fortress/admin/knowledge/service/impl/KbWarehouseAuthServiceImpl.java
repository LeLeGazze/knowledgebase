package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.dto.KbWarehouseAuthDto;
import com.castle.fortress.admin.knowledge.mapper.KbWarehouseAuthMapper;
import com.castle.fortress.admin.knowledge.service.KbWarehouseAuthService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * 主题知识仓库权限表 服务实现类
 *
 * @author
 * @since 2023-04-24
 */
@Service
public class KbWarehouseAuthServiceImpl extends ServiceImpl<KbWarehouseAuthMapper, KbWarehouseAuthEntity> implements KbWarehouseAuthService {
    /**
     * 获取查询条件
     *
     * @param kbWarehouseAuthDto
     * @return
     */
    public QueryWrapper<KbWarehouseAuthEntity> getWrapper(KbWarehouseAuthDto kbWarehouseAuthDto) {
        QueryWrapper<KbWarehouseAuthEntity> wrapper = new QueryWrapper();
        if (kbWarehouseAuthDto != null) {
            KbWarehouseAuthEntity kbWarehouseAuthEntity = ConvertUtil.transformObj(kbWarehouseAuthDto, KbWarehouseAuthEntity.class);
            wrapper.eq(kbWarehouseAuthEntity.getWhId() != null, "wh_id", kbWarehouseAuthEntity.getWhId());
            wrapper.eq(StrUtil.isNotEmpty(kbWarehouseAuthEntity.getCategory()), "category", kbWarehouseAuthEntity.getCategory());
            wrapper.eq(kbWarehouseAuthEntity.getWhAuth() != null, "wh_auth", kbWarehouseAuthEntity.getWhAuth());
            wrapper.eq(kbWarehouseAuthEntity.getKbAuth() != null, "kb_auth", kbWarehouseAuthEntity.getKbAuth());
            wrapper.eq("is_deleted", "2");
        }
        return wrapper;
    }


    @Override
    public IPage<KbWarehouseAuthDto> pageKbWarehouseAuth(Page<KbWarehouseAuthDto> page, KbWarehouseAuthDto kbWarehouseAuthDto) {
        QueryWrapper<KbWarehouseAuthEntity> wrapper = getWrapper(kbWarehouseAuthDto);
        wrapper.eq("is_deleted", "2");
        Page<KbWarehouseAuthEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbWarehouseAuthEntity> kbWarehouseAuthPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbWarehouseAuthDto> pageDto = new Page(kbWarehouseAuthPage.getCurrent(), kbWarehouseAuthPage.getSize(), kbWarehouseAuthPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbWarehouseAuthPage.getRecords(), KbWarehouseAuthDto.class));
        return pageDto;
    }


    @Override
    public List<KbWarehouseAuthDto> listKbWarehouseAuth(KbWarehouseAuthDto kbWarehouseAuthDto) {
        QueryWrapper<KbWarehouseAuthEntity> wrapper = getWrapper(kbWarehouseAuthDto);
        wrapper.eq("is_deleted", "2");
        List<KbWarehouseAuthEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbWarehouseAuthDto.class);
    }

    @Override
    public boolean saveAll(KbWarehouseAuthDto kbWarehouseAuthDto) {

        if (kbWarehouseAuthDto.getWhId() == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        // 往知识仓库目录添加用户
        List<KbWarehouseAuthDto> knowledgeAuthList = kbWarehouseAuthDto.getKnowledgeAuthList();
        if (knowledgeAuthList != null && knowledgeAuthList.size() > 0) {
            int index = 0;
            for (KbWarehouseAuthDto kbWarehouseAuthEntityDeptList : knowledgeAuthList) {
                kbWarehouseAuthEntityDeptList.setId(null);
                kbWarehouseAuthEntityDeptList.setCategory("00");
                kbWarehouseAuthEntityDeptList.setWhId(kbWarehouseAuthDto.getWhId());
                kbWarehouseAuthEntityDeptList.setSort(index++);
                KbWarehouseAuthEntity kbWarehouseAuthEntity = ConvertUtil.transformObj(kbWarehouseAuthEntityDeptList, KbWarehouseAuthEntity.class);

                baseMapper.insert(kbWarehouseAuthEntity);
            }
        }
        // 往知识仓库下知识添加用户 单个权限添加
        List<KbWarehouseAuthDto> authsList = kbWarehouseAuthDto.getAuthsList();
        if (authsList != null && authsList.size() > 0) {
            int index = 0;
            for (KbWarehouseAuthDto kbWarehouseAuthEntityUserList : authsList) {
                kbWarehouseAuthEntityUserList.setId(null);
                kbWarehouseAuthEntityUserList.setCategory("01");
                kbWarehouseAuthEntityUserList.setWhId(kbWarehouseAuthDto.getWhId());
                KbWarehouseAuthEntity kbWarehouseAuthEntity = ConvertUtil.transformObj(kbWarehouseAuthEntityUserList, KbWarehouseAuthEntity.class);
                kbWarehouseAuthEntity.setSort(index++);
                baseMapper.insert(kbWarehouseAuthEntity);
            }
        }
        List<KbWarehouseAuthDto> classAuthsList = kbWarehouseAuthDto.getClassAuthsList();

        if (classAuthsList != null && classAuthsList.size() > 0) {
            int index = 0;
            for (KbWarehouseAuthDto kbWarehouseAuthEntityDeptList : classAuthsList) {
                kbWarehouseAuthEntityDeptList.setId(null);
                kbWarehouseAuthEntityDeptList.setCategory("02");
                kbWarehouseAuthEntityDeptList.setWhId(kbWarehouseAuthDto.getWhId());
                KbWarehouseAuthEntity kbWarehouseAuthEntity = ConvertUtil.transformObj(kbWarehouseAuthEntityDeptList, KbWarehouseAuthEntity.class);
                kbWarehouseAuthEntity.setSort(index++);
                baseMapper.insert(kbWarehouseAuthEntity);
            }
        }
        return true;
    }

    @Override
    public boolean updateById(KbWarehouseAuthDto kbWarehouseAuthDto) {
        // 现将之前的删掉 在重新插入
        if (kbWarehouseAuthDto.getAuthsList() == null) {
            baseMapper.deleteHyWhid(kbWarehouseAuthDto.getWhId());
        } else {
            baseMapper.deleteWhid(kbWarehouseAuthDto.getWhId());
        }
        saveAll(kbWarehouseAuthDto);
//        // 查询出当前数据库已经存储了那些用户的权限
//        List<KbWarehouseAuthEntity> existings = baseMapper.findByWhId(kbWarehouseAuthDto.getWhId());
//
//        HashMap<String, KbWarehouseAuthEntity> existingsMap = new HashMap<>();
//        for (KbWarehouseAuthEntity existing : existings) {
//            String key = existing.getWhId() + "_" + existing.getUserId() + "_" + existing.getCategory();
//            existingsMap.put(key, existing);
//        }
//
//        List<KbWarehouseAuthEntity> deptList = kbWarehouseAuthDto.getDeptList();
//        if (deptList.size() > 0) {
//            for (KbWarehouseAuthEntity kbWarehouseAuthEntity : deptList) {
//                String key = kbWarehouseAuthDto.getWhId() + "_" + kbWarehouseAuthEntity.getUserId() + "_" + "00";
//                KbWarehouseAuthEntity mapKbWarehouseAuthEntity = existingsMap.get(key);
//                System.out.println("key = " + key + "   " + mapKbWarehouseAuthEntity);
//                // 当前用户在该知识下
//                if (mapKbWarehouseAuthEntity != null) {
//                    // 校验权限等级是否一致 不一致修改状态
//                    if (mapKbWarehouseAuthEntity.getWhAuth() != kbWarehouseAuthEntity.getWhAuth()) {
//                        mapKbWarehouseAuthEntity.setWhAuth(kbWarehouseAuthEntity.getWhAuth());
//                        baseMapper.updateById(mapKbWarehouseAuthEntity);
//                    }
//                } else {
//                    //新增用户权限直接插入数据库
//                    kbWarehouseAuthEntity.setCategory("00");
//                    kbWarehouseAuthEntity.setWhId(kbWarehouseAuthDto.getWhId());
//                    baseMapper.insert(kbWarehouseAuthEntity);
//                }
//            }
//        }
//        List<KbWarehouseAuthEntity> userList = kbWarehouseAuthDto.getUserList();
//        if (userList.size() > 0) {
//            for (KbWarehouseAuthEntity kbWarehouseAuthEntity : userList) {
//                String key = kbWarehouseAuthDto.getWhId() + "_" + kbWarehouseAuthEntity.getUserId() + "_" + "01";
//                KbWarehouseAuthEntity mapKbWarehouseAuthEntity = existingsMap.get(key);
//                // 当前用户在该知识下
//                if (mapKbWarehouseAuthEntity != null) {
//                    // 校验权限等级是否一致 不一致修改状态
//
//                    if (mapKbWarehouseAuthEntity.getKbAuth() != kbWarehouseAuthEntity.getKbAuth()) {
//                        mapKbWarehouseAuthEntity.setKbAuth(kbWarehouseAuthEntity.getKbAuth());
//                         baseMapper.updateById(mapKbWarehouseAuthEntity);
//                    }
//                } else {
//                    //新增用户权限直接插入数据库
//                    kbWarehouseAuthEntity.setCategory("01");
//                    kbWarehouseAuthEntity.setWhId(kbWarehouseAuthDto.getWhId());
//                    baseMapper.insert(kbWarehouseAuthEntity);
//                }
//            }
//        }
        return true;
    }

    @Override
    public ArrayList<KbWarehouseAuthEntity> findByUid(Long uid, String cateGory, Long swId) {

        return baseMapper.findByUid(uid, cateGory, swId);
    }

    /**
     * 根据登录的用户和知识目录获取这个用户的权限
     *
     * @param uid
     * @param swId
     * @param s
     * @return
     */
    @Override
    public ArrayList<KbWarehouseAuthEntity> findByUidAndSwid(Long uid, Long swId, String s) {
        return baseMapper.findByUidAndSwid(uid, swId, s);
    }

    @Override
    public List<KbWarehouseAuthDto> findByUidWarehouseAuth(Long uid, Long wh_id, Long wc_id) {
        QueryWrapper<KbWarehouseAuthEntity> wrapper = new QueryWrapper();
        // 知识目录不允许为空
        if (wh_id == null) {
            throw new BizException("知识目录ID不允许为空");
        }

        return baseMapper.findByUidWarehouseAuth(uid, wh_id, wc_id);
    }

    @Override
    public void deleteByWid(Long id) {
        baseMapper.deleteHyWhid(id);
    }

    @Override
    public List<KbWarehouseAuthDto> findBySwId(Long id) {
        return baseMapper.findByWhId(id);
    }

    @Override
    public KbWarehouseAuthDto findBySwIdAuth(Long swId) {
        KbWarehouseAuthDto kbWarehouseAuthDto = new KbWarehouseAuthDto();
        kbWarehouseAuthDto.setWhId(swId);
        // 查询目录权限
        Callable<List<KbWarehouseAuthDto>> knowledgeCallable = new Callable<List<KbWarehouseAuthDto>>() {

            @Override
            public List<KbWarehouseAuthDto> call() throws Exception {
                return baseMapper.findBySwIdAuth(swId, 00);
            }
        };
        // 查询目录下的分类
        Callable<List<KbWarehouseAuthDto>> classCallable = new Callable<List<KbWarehouseAuthDto>>() {
            @Override
            public List<KbWarehouseAuthDto> call() throws Exception {
                return baseMapper.findBySwIdAuth(swId, 02);
            }
        };
        FutureTask<List<KbWarehouseAuthDto>> knowledgeFuture = new FutureTask<>(knowledgeCallable);
        FutureTask<List<KbWarehouseAuthDto>> classFuture = new FutureTask<>(classCallable);
        new Thread(knowledgeFuture).start();
        new Thread(classFuture).start();
        try {
            kbWarehouseAuthDto.setKnowledgeAuthList(knowledgeFuture.get());
            kbWarehouseAuthDto.setClassAuthsList(classFuture.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kbWarehouseAuthDto;
    }

    @Override
    public ArrayList<KbWarehouseAuthEntity> findByUidVideo(Long uid, String cateGory) {

        return baseMapper.findByUidVideo(uid,cateGory);
    }
}

