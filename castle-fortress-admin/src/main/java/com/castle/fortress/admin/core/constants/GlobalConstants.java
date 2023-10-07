package com.castle.fortress.admin.core.constants;

/**
 * 全局常量
 * @author castle
 */
public interface GlobalConstants {
    /**
     * token的签名秘钥
     */
    String TOKEN_SIGN_KEY = "knowledgeBase";
    /**
     * 请求头中token的key值
     */
    String TOKEN_HEADER_KEY="Authorization";
    /**
     * token的有效期 分钟
     */
    Integer TOKEN_EXPIRATION_MINUTE=60*10;
    /**
     * 请求头中refreshToken的key值
     */
    String REFRESH_TOKEN_HEADER_KEY="token";
    /**
     * refreshToken的有效期 小时
     */
    Integer REFRESH_TOKEN_EXPIRATION_HOUR=24*31;
    /**
     * API对外开放接口前缀
     */
    String API_PREFIX = "/openapi/";
    /**
     * 秘钥对请求头的secret_id
     */
    String REQUEST_HEADER_SECRET_ID = "C-Secret";
    /**
     * 秘钥对请求头的请求时间
     */
    String REQUEST_HEADER_DATE = "C-Date";
    /**
     * 秘钥对请求头的请求签名
     */
    String REQUEST_HEADER_SIGN = "Sign";
    /**
     * 白名单 不需要登录可访问
     * 两个星号表示通配
     */
    String[] WHITE_LIST ={
            //登录页面
            "/login"
//            微信小程序登录
            ,"/memberLogin/wxAppLogin"
//            微信小程序绑定手机号
            ,"/bindPhoneNumber"

            //运营管理端 生成微信登录二维码
            ,"/wxLoginCode"
            //运营管理端 微信扫码登录
            ,"/wxQrCodeLogin"
            //运营管理端 微信绑定并登录
            ,"/bindingWeChatLogin"
            //运营管理端 个人中心微信二维码
            ,"/wxBindCode"
            //运营管理端 个人中心微信绑定
            ,"/wxQrCodeBind"

            //运营管理端 生成QQ登录二维码
            ,"/qqLoginCode"
            //运营管理端 QQ回调
            ,"/qqCallBack"
            //运营管理端 QQ绑定并登录
            ,"/bindingQqLogin"
            //运营管理端 个人中心QQ二维码
            ,"/qqBindCode"
            //运营管理端 个人中心QQ绑定 回调
            ,"/qqBindCallBack"

            //运营管理端 企微登录回调
            ,"/entWetchatLogin"
            //运营管理端 生成钉钉登录二维码
            ,"/dingLoginCode"
            //运营管理端 钉钉登录回调
            ,"/dingQrCodeLogin"
            // 获取是否启用第三方登录
            ,"/system/configApi/getSetting"

//            //登录请求
//            ,"login"
            //静态资源
            ,"/static/**"
            //cms首页
            ,"/index.*","/index","/"
            //cms样式
            ,"/css/**"
            //cmsjs
            ,"/js/**"
            //cms图片
            ,"/images/**"
            //cms favicon.ico
            ,"/favicon.ico"
            //cms 封面
            ,"/cmscoverpng/**"
            //页面类都放行
            ,"/page/**"
            //刷新token
            ,"/refreshToken"
            //swagger
            ,"/webjars/**","/v2/**","/doc.html","/swagger-ui.html","/swagger-resources"
            //文件上传
            ,"/system/oss/upload"
            //ckeditor文件上传
            ,"/system/oss/ckupload"
            //ckeditor粘贴上传
            ,"/system/oss/ckupload&responseType=json"
            //验证码
            ,"/captcha/**"
            //对外接口
            ,API_PREFIX+"**"
            //动态表 字段
            ,"/system/tmpDemo/demo"
            //表单数据沉淀
            ,"/formManage/saveData"
            //表单数据查询
            ,"/formManage/info"
            //表单发送验证码
            ,"/formManage/getCode"
            //公网访问获取表单配置
            ,"/form/formConfig/info"
            //发送验证码
            ,"/api/sendCode"
            //验证码登录
            ,"/memberLogin/codeLogin"
            //微信登录
            ,"/wxLogin"
            //测试网络流畅接口
            ,"/system/tmpDemo/hello"
            //错误页面
            ,"/404"
            //回调
            ,"/demo/callbackGet"
            ,"/demo/callbackPost"
            ,"/demo/callback"
            //支付
            ,"/topay/**"
            //企业微信发送消息demo
            ,"/api/message/wecom/test"
            //钉钉发送消息demo
            ,"/api/message/dingtalk/test"
            ,"/knowledge/kbVideVersion/getContentComparison"
            ,"/knowledge/kbVideVersion/diff/**"
            ,"/knowledge/kbVideVersion/filePreview"
            ,"/knowledge/kbVideVersion/filePreviewExecl"
            // 协议管理
            ,"/api/system/protocol/getByCode"


    };
    /**
     * XSS过滤白名单 不需要替换标签
     * 两个星号表示通配
     */
    String[] XSS_WHITE_LIST ={
            //代码示例编辑 富文本
            "/demo/tmpDemoGenerate/edit"
            //表单数据沉淀
            ,"/formManage/saveData"
            //发送验证码
            ,"/api/sendCode"
            //验证码登录
            ,"/api/member/codeLogin"
            //微信登录
            ,"/wxLogin"
            //cms模板内容修改
            ,"/cms/cmsTemplate/save"
            //流程图设计
            ,"/flowable/flowableTemplate/edit"

    };
    /**
     * 默认的页面下标
     */
    Integer DEFAULT_PAGE_INDEX = 1;
    /**
     * 默认的页面记录数
     */
    Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 运维人员用户名密码
     */
    String SUPER_ADMIN_NAME="root";
    String ROOT_PWD = "97531";
    Boolean ROOT_FLAG = true;
    /**
     * job名称前缀
     */
    String JOB_NAME_PREFIX="CASTLE_TASK_";
    /**
     * job所属组名称
     */
    String JOB_GROUP_DEFAULE = "CASTLE_FORTRESS";
    /**
     * 触发器名称前缀
     */
    String TRIGGER_NAME_PREFIX = "CASTLE_TRIGGER_";
    /**
     * 触发器组名称
     */
    String TRIGGER_GROUP_DEFAULT = "CASTLE_FORTRESS";

}
