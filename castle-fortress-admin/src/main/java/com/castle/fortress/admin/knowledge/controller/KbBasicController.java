package com.castle.fortress.admin.knowledge.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.dto.*;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicUserEntity;
import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.mapper.KbWarehouseAuthMapper;
import com.castle.fortress.admin.knowledge.service.*;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ucar.nc2.dt.radial.UF2Dataset;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 知识基本信息表
 */

@Api(tags = "知识基本信息表理控制器")
@Controller
public class KbBasicController {
    @Autowired
    private KbBasicService kbBasicService;
    @Autowired
    private KbBasicUserService kbBasicUserService;

    @Autowired
    private KbVideVersionService kbVideVersionService;
    @Autowired
    private KbWarehouseAuthMapper kbWarehouseAuthMapper;
    @Autowired
    private KbWarehouseAuthService kbWarehouseAuthService;

    @CastleLog(operLocation = "知识基本信息", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识基本信息展示")
    @GetMapping("/knowledge/kbBasicWarehouse/page")
    @ResponseBody
    public RespBody<IPage<KbBaseShowDto>> pageKbBasicWarehouse(KbBaseShowDto kbBasicDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbBaseShowDto> page = new Page(pageIndex, pageSize);
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        if (kbBasicDto.getExtend() != null) {
            kbBasicDto.setExtend(new String(Base64.getDecoder().decode(kbBasicDto.getExtend())));
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            IPage<KbBaseShowDto> pages = kbBasicService.pageKbBaseicWarehouseExtendsAdmin(page, kbBasicDto);
            return RespBody.data(pages);
        } else {

            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", Long.parseLong(kbBasicDto.getSwId()));
            if (uAuths == null || uAuths.size() == 0) {
                Page<KbBaseShowDto> tmpPage = new Page(pageIndex, pageSize);
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
            IPage<KbBaseShowDto> pages = kbBasicService.pageKbBaseicWarehouseExtends(page, kbBasicDto, uid, integers);
            return RespBody.data(pages);
        }
    }


    @CastleLog(operLocation = "推荐知识", operType = OperationTypeEnum.QUERY)
    @ApiOperation("推荐知识")
    @GetMapping("/knowledge/kbBasicWarehouse/randBasicPage")
    @ResponseBody
    public RespBody<List<KbBaseShowDto>> randBasicPage(@RequestParam(required = false) String swId, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbBaseShowDto> page = new Page<>(pageIndex, pageSize);
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbBaseShowDto> pages = kbBasicService.randBasicPageAdmin(page, swId);
            return RespBody.data(pages);
        } else {
            if (StringUtils.isNotEmpty(swId)) {
                // 校验该用户是否有此知识目录权限
                ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", Long.parseLong(swId));
                if (uAuths == null || uAuths.size() == 0) {
                    return RespBody.data(new ArrayList<>());
                }
            }
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            List<KbBaseShowDto> pages = kbBasicService.randBasicPage(page, uid, integers, swId);
            return RespBody.data(pages);
        }
    }

    @CastleLog(operLocation = "最新发布知识", operType = OperationTypeEnum.QUERY)
    @ApiOperation("最新发布知识")
    @GetMapping("/knowledge/kbBasicWarehouse/newBasicList")
    @ResponseBody
    public RespBody<List<KbBaseShowDto>> newBasicList() {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbBaseShowDto> list = kbBasicService.newBasicListAdmin();
            return RespBody.data(list);
        } else {
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            List<KbBaseShowDto> list = kbBasicService.newBasicList(uid, integers);
            return RespBody.data(list);
        }
    }

    @CastleLog(operLocation = "最近预览知识", operType = OperationTypeEnum.QUERY)
    @ApiOperation("最近预览知识")
    @GetMapping("/knowledge/kbBasicWarehouse/recentPreviewBasicList")
    @ResponseBody
    public RespBody<List<KbBaseShowDto>> recentPreviewBasicList(@RequestParam(required = false) String swId, @RequestParam(required = false) Integer size) {
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbBaseShowDto> list = kbBasicService.recentPreviewBasicListAdmin(uid, swId, pageSize);
            return RespBody.data(list);
        } else {
            if (StringUtils.isNotEmpty(swId)) {
                // 校验该用户是否有此知识目录权限
                ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", Long.parseLong(swId));
                if (uAuths == null || uAuths.size() == 0) {
                    return RespBody.data(new ArrayList<>());
                }
            }
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            List<KbBaseShowDto> list = kbBasicService.recentPreviewBasicList(uid, integers, swId, pageSize);
            return RespBody.data(list);
        }
    }

    @GetMapping("/kb/kbBasic/findBasic")
    @ApiOperation("知识详情查询")
    @ResponseBody
    public RespBody<KbModelTransmitDto> findBasic(@RequestParam Long id, @RequestParam(required = false) Integer num) throws Exception {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        Long uid = sysUser.getId();

        // 校验该用户是否有此知识目录权限
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            //查询当前知识的信息

            KbModelTransmitDto allByBasic = kbBasicService.findAllByBasic(id);
            allByBasic.setDownloadAuthority(false);
            allByBasic.setUpdateAuthority(true);
            allByBasic.setDeleteCommentsAuthority(true);
            // 未发布 浏览量不加 和不允许下载
            if (allByBasic.getStatus() == 1) {
                savaKbBasic(num, id, uid);
                allByBasic.setDownloadAuthority(true);
                allByBasic.setReadCount(allByBasic.getReadCount()+1);
            }

            return RespBody.data(allByBasic);
        } else {
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            KbBasicEntity allByBasic = kbBasicService.findByIdAuth(uid, integers, id);
            KbBasicEntity byId = kbBasicService.getById(id);
            if (allByBasic == null && !byId.getAuth().equals(uid)) {
                throw new BizException("暂无权限，请联系管理员授权");
            }
            // 只有发布能增加浏览量
            if (byId.getStatus() == 1) {
                savaKbBasic(num, id, uid);
            }
            //校验是否有下载权限
            Callable<KbBasicEntity> downloadCallable = new Callable<KbBasicEntity>() {
                @Override
                public KbBasicEntity call() throws Exception {
                    List<Integer> integers = Arrays.asList(
                            KbAuthEnum.DOWNLOAD.getCode(),
                            KbAuthEnum.UPDATE.getCode(),
                            KbAuthEnum.MANAGE.getCode()
                    );
                    return kbBasicService.findByIdAuth(uid, integers, id);
                }
            };
            // 校验是否有编辑 权限
            Callable<KbBasicEntity> updateCallable = new Callable<KbBasicEntity>() {
                @Override
                public KbBasicEntity call() throws Exception {
                    List<Integer> integers = Arrays.asList(
                            KbAuthEnum.UPDATE.getCode(),
                            KbAuthEnum.MANAGE.getCode()
                    );
                    return kbBasicService.findByIdAuth(uid, integers, id);
                }
            };
            // 校验是否有删除评论权限
            Callable<KbBasicEntity> deleteCallable = new Callable<KbBasicEntity>() {
                @Override
                public KbBasicEntity call() throws Exception {
                    List<Integer> integers = Arrays.asList(KbAuthEnum.MANAGE.getCode());
                    return kbBasicService.findByIdAuth(uid, integers, id);
                }
            };
            KbModelTransmitDto kbModelTransmitDot = kbBasicService.findAllByBasic(id);
            FutureTask<KbBasicEntity> downloadFuture = new FutureTask<>(downloadCallable);
            FutureTask<KbBasicEntity> updateFuture = new FutureTask<>(updateCallable);
            FutureTask<KbBasicEntity> deletedFuture = new FutureTask<>(deleteCallable);
            new Thread(downloadFuture).start();
            new Thread(updateFuture).start();
            new Thread(deletedFuture).start();
            kbModelTransmitDot.setDownloadAuthority(downloadFuture.get() != null);
            kbModelTransmitDot.setUpdateAuthority(updateFuture.get() != null);
            kbModelTransmitDot.setDeleteCommentsAuthority(deletedFuture.get() != null);
            return RespBody.data(kbModelTransmitDot);
        }

    }


    /**
     * 知识批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识批量删除")
    @PostMapping("/knowledge/kbBasic/deleteBatch")
    @ResponseBody
//    @RequiresPermissions("/kb:kbModel:delete")
    public RespBody<String> deleteKbBasicLabelBatch(@RequestBody List<Long> ids) {
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
            int deleteByIds = kbBasicService.deleteByIdsAdmin(ids);
            if (deleteByIds > 0) {
                return RespBody.data("删除成功");
            }
            return RespBody.fail("删除失败");
        } else {
            //只有管理员才有删除权限
            List<Integer> integers = Arrays.asList(KbAuthEnum.MANAGE.getCode());
            List<Long> kbWarehouseAuthEntityList = kbWarehouseAuthMapper.findByUidCategory(uid, integers);
            String res = kbBasicService.deleteByIds(kbWarehouseAuthEntityList, ids);
            if (!res.contains("无权限删除该分类下的内容")) {
                return RespBody.data("删除成功");
            }
            return RespBody.fail(res);
        }

    }


    @ApiOperation("更新数据")
    @PostMapping("/kb/modelBaisc/updataData")
    @ResponseBody
    public RespBody<String> updateData(@RequestBody KbModelAcceptanceDto formDataDto) {
        if (CommonUtil.verifyParamNull(formDataDto, formDataDto.getModelId())) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        kbBasicService.updateDate(formDataDto);
        return RespBody.data("保存成功");
    }


    @ApiOperation("我的知识查询")
    @GetMapping("/kb/modelBaisc/selectBasic")
    @ResponseBody
    public RespBody<IPage<KbModelTransmitDto>> selectBasicByLike(KbBaseShowDto kbBaseShowDto,
                                                                 @RequestParam(required = false) Integer current,
                                                                 @RequestParam(required = false) Integer size) throws Exception {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelTransmitDto> page = new Page<>(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        if (kbBaseShowDto.getStatus() == 2) {
            if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
                IPage<KbModelTransmitDto> pages = kbBasicService.findBasicByLikeAdmin(page, kbBaseShowDto);
                return RespBody.data(pages);
            } else {
                List<Integer> integers = Arrays.asList(
                        KbAuthEnum.SHOW.getCode(),
                        KbAuthEnum.DOWNLOAD.getCode(),
                        KbAuthEnum.UPDATE.getCode(),
                        KbAuthEnum.MANAGE.getCode()
                );
                kbBaseShowDto.setCreateUser(userId);
                IPage<KbModelTransmitDto> pages = kbBasicService.findBasicByUploud(page, kbBaseShowDto, userId, integers);
                return RespBody.data(pages);
            }
        } else {
            if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
                IPage<KbModelTransmitDto> pages = kbBasicService.findBasicByLikeAdmin(page, kbBaseShowDto);
                return RespBody.data(pages);
            } else {
                List<Integer> integers = Arrays.asList(
                        KbAuthEnum.SHOW.getCode(),
                        KbAuthEnum.DOWNLOAD.getCode(),
                        KbAuthEnum.UPDATE.getCode(),
                        KbAuthEnum.UPDATE.getCode(),
                        KbAuthEnum.MANAGE.getCode()
                );
                kbBaseShowDto.setCreateUser(userId);
                IPage<KbModelTransmitDto> pages = kbBasicService.findBasicByLike(kbBaseShowDto, page, userId, integers);
                return RespBody.data(pages);
            }
        }
    }


    private void savaKbBasic(Integer num, Long id, Long uid) {
        if (num != null && num == 1) {
            KbBasicUserEntity kbBasicUserEntity = new KbBasicUserEntity();
            kbBasicUserEntity.setBId(id);
            kbBasicUserEntity.setUserId(uid);
            kbBasicUserEntity.setType(1);
            kbBasicUserEntity.setStatus(1);
            kbBasicUserService.save(kbBasicUserEntity);
        }

    }

    @CastleLog(operLocation = "知识下载", operType = OperationTypeEnum.QUERY)
    @ApiOperation("下载")
    @PostMapping("/knowledge/kbBasicUser/insertDownload")
    @ResponseBody
    public void downloadInsert(@RequestBody KbVideVersionDto kbVideVersionDto, HttpServletResponse response) {
        if (kbVideVersionDto == null || (kbVideVersionDto.getBId() == null && kbVideVersionDto.getVides() == null || kbVideVersionDto.getVides().size() == 0)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        kbVideVersionService.downloadFile(response, kbVideVersionDto.getBId(), sysUser.getRealName(), kbVideVersionDto.getVides());
        kbBasicUserService.insertDownLoad(kbVideVersionDto.getBId());
    }

    @CastleLog(operLocation = "知识预览", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识预览")
    @PostMapping("/knowledge/kbBasicUser/fileShow")
    @ResponseBody
    public void fileShow(@RequestBody KbVideVersionDto kbVideVersionDto, HttpServletResponse response) {
        if (kbVideVersionDto == null || (kbVideVersionDto.getBId() == null && kbVideVersionDto.getVides() == null || kbVideVersionDto.getVides().size() == 0)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        kbVideVersionService.fileShow(response, kbVideVersionDto.getBId(), kbVideVersionDto.getVides());
    }

    @ApiOperation("所有作者查询")
    @GetMapping("/kb/modelBaisc/authAll")
    @ResponseBody
    public RespBody<List<SysUser>> authAll() {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<SysUser> basicByLike = kbBasicService.findAllAuth();
            return RespBody.data(basicByLike);
        } else {
            List<Integer> asList = Arrays.asList(KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            List<SysUser> basicByLike = kbBasicService.findBaseAuth(asList, uid);
            return RespBody.data(basicByLike);
        }
    }

    @ApiOperation("最新12个月数量")
    @GetMapping("/knowledge/kbBasicUser/theLatest12Quantities")
    @ResponseBody
    public RespBody<List<Map<String, Object>>> getTheLatest12Quantities() {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<Map<String, Object>> basicByLike = kbBasicService.getTheLatest12QuantitiesAdmin();
            return RespBody.data(basicByLike);
        } else {
            List<Integer> asList = Arrays.asList(KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            List<Map<String, Object>> basicByLike = kbBasicService.getTheLatest12Quantities(asList, uid);
            return RespBody.data(basicByLike);
        }
    }

    @ApiOperation("知识库top7")
    @GetMapping("/knowledge/kbBasicUser/knowledgeBaseTopN")
    @ResponseBody
    public RespBody<List<Map<String, Object>>> getKnowledgeBaseTopN(@RequestParam Integer num) {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        if (num == null || num < 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<Map<String, Object>> basicByLike = kbBasicService.getKnowledgeBaseTopNAdmin(num);
            return RespBody.data(basicByLike);
        } else {
            List<Integer> asList = Arrays.asList(KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            List<Map<String, Object>> basicByLike = kbBasicService.getKnowledgeBaseTopN(asList, uid, num);
            return RespBody.data(basicByLike);
        }
    }

}
