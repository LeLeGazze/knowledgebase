package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.entity.KbVideVersionEntity;
import com.castle.fortress.admin.knowledge.dto.KbVideVersionDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.List;

/**
 * PDF/word 等转换成PDF 服务类
 *
 * @author 
 * @since 2023-05-08
 */
public interface KbVideVersionService extends IService<KbVideVersionEntity> {

    /**
     * 分页展示PDF/word 等转换成PDF列表
     * @param page
     * @param kbVideVersionDto
     * @return
     */
    IPage<KbVideVersionDto> pageKbVideVersion(Page<KbVideVersionDto> page, KbVideVersionDto kbVideVersionDto);

    /**
    * 分页展示PDF/word 等转换成PDF扩展列表
    * @param page
    * @param kbVideVersionDto
    * @return
    */
    IPage<KbVideVersionDto> pageKbVideVersionExtends(Page<KbVideVersionDto> page, KbVideVersionDto kbVideVersionDto);
    /**
    * PDF/word 等转换成PDF扩展详情
    * @param id PDF/word 等转换成PDFid
    * @return
    */
    KbVideVersionDto getByIdExtends(Long id);

    /**
     * 展示PDF/word 等转换成PDF列表
     * @param kbVideVersionDto
     * @return
     */
    List<KbVideVersionDto> listKbVideVersion(KbVideVersionDto kbVideVersionDto);

    void tmpSave(KbModelAcceptanceDto formDataDto);

    void tmpUpdate(Long id, KbModelAcceptanceDto formDataDto);

    void  downloadFile(HttpServletResponse response, Long basicId, String userName, List<Long> vides);
    void  fileShow(HttpServletResponse response, Long basicId, List<Long> vides);

    List<KbVideVersionDto> findByTypeVersion(KbVideVersionDto kbVideVersionDto);

    Object getContentComparison(Model model, String sourceId, String targetId);

    String getFilePreview(Model model, String id);

    String getfilePreviewBase64(String id);
}
