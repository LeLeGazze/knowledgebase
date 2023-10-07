package com.castle.fortress.common.respcode;

import com.castle.fortress.common.entity.IRespCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务通用错误码
 * 用户模块 2000~2999
 * 系统模块 3000~3999
 * API模块 4000~4999
 * 消息模块 5000~5999
 * 表单模块 6000~6999
 * 会员模块 7000~7999
 * CMS模块 8000~8999
 * 绑定第三方API 模块 9000~9999
 * FLOWABLE模块 10000~10999
 * OA模块  11000~11999
 * @author Dawn
 */
@Getter
@AllArgsConstructor
public enum BizErrorCode implements IRespCode {
    /**
     * 用户名或密码错误
     */
    PWD_ERROR(2000,"用户名或密码错误"),
    DEL_ROLE_EXIST_USER_ERROR(2001,"该角色绑定用户,请解绑后再操作"),
    DEL_DEPT_EXIST_USER_ERROR(2002,"该部门绑定用户,请解绑后再操作"),
    DEL_POST_EXIST_USER_ERROR(2003,"该职位绑定用户,请解绑后再操作"),
    DEPT_NAME_EXIST_ERROR(2004,"该部门名称已存在"),
    POST_NAME_EXIST_ERROR(2005,"该职位名称已存在"),
    USER_NAME_EXIST_ERROR(2006,"登录名不允许重复"),
    USER_PHONE_EXIST_ERROR(2007,"该手机已绑定"),
    ROLE_NAME_EXIST_ERROR(2008,"该角色名已存在"),
    OLD_PWD_ERROR(2009,"原密码错误"),
    USER_EMAIL_EXIST_ERROR(2010,"该邮箱已绑定"),
    OLD_PHONE_ERROR(2011,"原手机号错误"),
    POST_SELF_ERROR(2012,"不可选择本职位作为上级职位"),
    USER_NAME_lIMITED_ERROR(2013,"该用户名为系统限制级用户，禁止操作"),
    USER_DEL_SELF_ERROR(2014,"当前账号已登录，无法删除当前账号"),
    WX_USER_ERROR(2015,"用户不存在"),
    WX_BIND_ERROR(2016,"此账号已被绑定"),
    WX_NO_UNIONID_ERROR(2017,"读取微信信息出错,请重新扫码"),
    WX_UNIONID_EXIST_ERROR(2018,"该微信号已经被绑定,请换号重试"),
    PHONE_NO_EXIST_ERROR(2019,"该账号不存在"),
    QQ_NO_OPENID_ERROR(2020,"读取QQ信息出错,请重新扫码"),
    QQ_OPENID_EXIST_ERROR(2021,"该QQ号已经被绑定,请换号重试"),
    USER_STATUS_ERROR(2022,"用户状态异常，请联系管理员"),
    /**
     * 系统模块
     */
    FT_CONFIG_ERROR(3000,"文件存储配置错误"),
    FT_UPLOAD_EMPTY_ERROR(3001,"上传文件为空"),
    DICT_CODE_EXIST_ERROR(3002,"字典编码已存在"),
    DICT_VALUE_EXIST_ERROR(3003,"字典值已存在"),
    JOB_ERROR(3004,"定时任务异常"),
    JOB_TASK_NAME_EXIST_ERROR(3005,"该任务已存在,请重新指定任务"),
    FT_UPLOAD_ERROR(3006,"文件上传异常"),
    PARENT_IS_SON_ERROR(3007,"不可选下级作为上级"),
    FILE_NO_EXIST_ERROR(3008,"文件不存在"),
    FILE_TYPE_ERROR(3009,"文件类型错误"),
    PAY_SCENE_CODE_EXIST_ERROR(3010, "场景编码已存在"),
    PAY_SCENE_DEL_ERROR(3011, "场景下有生效的支付方式，请禁用后删除"),
    PAY_CONFIG_ERROR(3012, "配置错误, 请检查是否漏了配置项"),
    PAY_UNIORDER_WX_ERROR(3013, "微信统一下单异常"),
    PAY_UNIORDER_ALI_ERROR(3014, "支付宝统一下单异常"),
    PAY_REFOUND_WX_ERROR(3015, "微信退款异常"),
    PAY_REFOUND_ALI_ERROR(3016, "支付宝退款异常"),
    PAY_ASYNC_SIGN_VERIFY_FAIL(3017, "异步返回签名失败"),
    ALIPAY_NOTIFY_ID_VERIFY_FAIL(3018, "【支付宝web端支付验证签名】验证notifyId失败"),
    ALIPAY_ASYNC_SIGN_VERIFY_FAIL(3019, "【支付宝web端支付同步返回验证签名】验证签名失败"),
    PAY_SYNC_SIGN_VERIFY_FAIL(3020, "同步返回签名失败"),
    PAY_TYPE_ERROR(3021, "错误的支付方式"),
    ALIPAY_TRADE_STATUS_IS_NOT_SUCCESS(3022, "支付宝交易状态不是成功"),
    ALIPAY_TIME_FORMAT_ERROR(3023, "支付宝返回的时间格式不对"),
    PAY_ORDER_EXIST_ERROR(3024, "商户订单号重复"),
    NO_BANK_DATA(3025, "未查询到银行信息"),

    /**
     * API模块
     */
    CONTENT_TYPE_ERROR(4000, "Content-Type类型错误"),
    DEFECT_HEAD(4001, "缺失请求头"),
    TIME_OUT_ERROR(4002, "请求超时"),
    REQUEST_DATE_ERROR(4003, "请求日期格式错误"),
    REQUEST_METHOD_ERROR(4004, "请求方法错误"),
    NET_CONFIG_ERROR(4005, "网络配置错误"),
    AUTH_TYPE_ERROR(4006, "鉴权类型错误"),
    SIGN_ERROR(4007, "签名错误"),
    NO_TIMES_ERROR(4008,"调用次数已用光"),
    SECRET_EXPIRE_ERROR(4009,"秘钥已过期"),
    TIME_EXPIRE_ERROR(4010,"请求已过期"),
    WHITE_IP_ERROR(4011,"请求不在ip白名单"),
    SECRET_ERROR(4012, "秘钥错误"),
    REQUEST_DATE_ADVANCED_ERROR(4013, "请求日期错误"),

    /**
     * 消息模块
     */
    SMSCODE_EXIST_ERROR(5000,"短信编码已存在"),
    SMSCODE_ERROR(5001,"短信编码错误"),
    SMS_SEND_ERROR(5002,"短信发送失败"),
    MAILCODE_EXIST_ERROR(5003,"邮箱编码已存在"),
    MAILCODE_NO_EXIST_ERROR(5004,"邮箱编码不存在"),

    /**
     * 表单模块
     */
    FORM_TABLE_EXIST_ERROR(6000,"表名已存在"),
    FORM_TABLE_DEL_ERROR(6001,"该表不允许删除"),
    FORM_COL_EMPTY_ERROR(6002,"请设置有效的表字段"),
    FORM_COL_REPEAT_ERROR(6003,"数据重复"),


    /**
     * 会员模块
     */
    MEMBER_INFO_PAST(7000,"获取登录信息失败,请尝试重新登录"),

    /**
     * CMS模块
     */
    CMS_COLUMNS_CODE_EXIST_ERROR(8000,"栏目标识已存在"),
    CMS_LINKGROUP_CODE_EXIST_ERROR(8001,"友链分组标识已存在"),
    CMS_ARTICLTLIST_CODE_EXIST_ERROR(8002,"文章分类标识已存在"),
    CMS_TEMP_ERROR(8003,"模板库配置错误，请联系管理员"),
    CMS_TAG_CODE_EXIST_ERROR(8004,"标签已存在"),
    CMS_CODE_EXIST_ERROR(8005,"编码已存在"),
    CMS_MODEL_CODE_ALTER_ERROR(8006,"模型编码修改错误"),
    CMS_COL_REPEAT_ERROR(8007,"字段名重复"),
    CMS_PROP_REPEAT_ERROR(8008,"引用名重复"),

    /**
     * 绑定第三方API
     */
    BIND_PLATFORM_ERROR(9000,"云平台信息未绑定"),
    CONFIG_ERROR(9001,"未配置或配置出错"),

    /**
     * FLOWABLE 模块
     */
    ACT_KEY_ERROR(10000,"流程编码错误"),
    TASK_ID_ERROR(10001,"任务编码错误"),
    FLOWABLE_CONFIG_ERROR(10002,"流程配置错误"),
    ACT_KEY_EXIST_ERROR(10003,"流程编码已存在"),
    PROCESS_CANCEL_ERROR(10004,"无可取消的流程"),
    PROCESS_ENDED_ERROR(10005,"无可结束的流程"),
    PROCESS_BACK_ERROR(10006,"无可回退的流程"),


    /**
     * OA 模块
     */
    NO_CONFIG(11000, "不存在对应设置"),
    NO_CONFIG_DATE(11001, "不存在对应时间设置"),
    NO_WORKDAY(11002, "非工作日无需打卡"),
    NOT_ENOUGH_DISTANCE(11003, "超出打卡距离"),
    TIME_NOT_YET(11004, "未到打卡时间"),
    NO_RANGE(11005, "缺少经纬度"),
    NO_PHOTO(11006, "缺少照片"),
    TIME_ERROR(11007, "超出打卡时间"),
    NOT_REPEATABLE(11008, "不可重复打卡"),
    UNKNOWN_EXCEPTION(11009, "未知异常"),
    NO_ADDRESS(11010, "缺少详细地址"),

    /**
     * 订单模块
     */
    ITEM_CANNOT_BE_EMPTY(12000, "商品不能为空"),

    ;

    final int code;
    String msg;

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
