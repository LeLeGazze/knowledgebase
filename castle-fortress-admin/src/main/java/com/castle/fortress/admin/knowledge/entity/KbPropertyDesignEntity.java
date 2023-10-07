package com.castle.fortress.admin.knowledge.entity;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.ArrayList;

/**
 * 模型属性设计表 实体类
 *
 * @author Pan Chen
 * @since 2023-04-11
 */
@Data
@TableName("kb_property_design")
//@EqualsAndHashCode(callSuper = true)
public class KbPropertyDesignEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 与模型表的关联字段
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modelId;
    /**
     * 属性名字
     */
    private String name;
    /**
     * 属性编码（用于代码中引用）
     */
    private String propName;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 是否为必填项
     */
    private Integer isRequired;
    /**
     * 是否为筛选项
     */
    private Integer isFiltrate;
    /**
     * 表单类型（短文本、文本等）
     */
    private Integer formType;
    /**
     * 列表类型数据配置
     */
    private String dataType;
    /**
     * 附件
     */
    private String attachment;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 下拉框
     */
    private String selectFrame;

    public void setSelectFrame(Object selectFrame) {
        if (selectFrame instanceof ArrayList) {
            this.selectFrame = JSONObject.toJSONString(selectFrame);
        } else {
            String s = String.valueOf(selectFrame);
            if (StrUtil.isEmpty(s.replaceAll("null", ""))) {
                this.selectFrame = "";
            } else {
                this.selectFrame = String.valueOf(selectFrame);
            }

        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;//地址相等
        }
        if (obj == null) {
            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
        }
        if (obj instanceof KbPropertyDesignEntity) {
            KbPropertyDesignEntity other = (KbPropertyDesignEntity) obj;
            //需要比较的字段相等，则这两个对象相等
            if (BizCommonUtil.equalsStr(this.getPropName(), other.getPropName())
                    && BizCommonUtil.equalsStr(this.dataType, other.dataType)
                    && BizCommonUtil.equalsStr(this.name, other.name)
            ) {
                return true;
            }
        }
        return false;
    }
}
