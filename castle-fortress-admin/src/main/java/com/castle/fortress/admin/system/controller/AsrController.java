package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.common.entity.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 语音相关接口 控制器
 * @author castle
 */
@Api(tags = "文件传输控制器")
@RestController
public class AsrController {

    // 腾讯云
    public static String APP_ID = "1251334741";
    public static String SECRET_ID = "AKIDkIclfKWvrXyA6mwNIxYtynMfjaDblkSu";
    public static String SECRET_KEY = "JNBiyyiBNK0QZR3SbU7UbR3OTIsCG3bu";
    /**
     * 获取实时语音输入websocket链接 官网文档 https://cloud.tencent.com/document/product/1093/48982
     * @return
     */
    @ApiOperation("获取实时语音输入websocket链接")
    @GetMapping("/system/getAsrWs")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "engineModelType", value = "引擎模型类型  8k_zh：电话场景 8k 中文普通话通用，16k_zh：非电话场景 16k 中文普通话通用", paramType = "body",required = true, dataType="String") ,
            @ApiImplicitParam(name = "voiceFormat", value = "语音编码方式 1：pcm；4：speex(sp)；6：silk；8：mp3；10：opus（opus 格式音频流封装说明）；12：wav；14：m4a（每个分片须是一个完整的 m4a 音频）；16：aac", paramType = "body",required = true, dataType="String") ,
    })
    public RespBody getAsrWs(@RequestParam Map<String , String> params){

        if (StrUtil.isEmpty(params.get("engineModelType")) || StrUtil.isEmpty(params.get("voiceFormat"))){
            return RespBody.fail("参数错误");
        }
        String engineModelType = params.get("engineModelType");
        String voiceFormat = params.get("voiceFormat");
        Map<String , Object> map = new HashMap<>();
        map.put("secretid" , SECRET_ID);//腾讯云注册账号的密钥 SecretId
        Long nowTime = System.currentTimeMillis()/1000;
        map.put("timestamp" , nowTime+"");// 当前 UNIX 时间戳，单位为秒。如果与当前时间相差过大，会引起签名过期错误
        map.put("expired" , nowTime + 30*24*60 + "");// 签名的有效期截止时间 UNIX 时间戳，单位为秒。expired 必须大于 timestamp 且 expired - timestamp 小于90天
        map.put("nonce" , "123456");// 随机正整数。用户需自行生成，最长10位
//        map.put("engine_model_type" , "8k_zh");// 引擎模型类型 8k_zh：电话场景 8k 中文普通话通用，16k_zh：非电话场景 16k 中文普通话通用 类型比较多 详细看官网:https://cloud.tencent.com/document/product/1093/48982
        map.put("engine_model_type" ,engineModelType);
//        map.put("voice_format" , "8");// 语音编码方式，可选，默认值为4。1：pcm；4：speex(sp)；6：silk；8：mp3；10：opus（opus 格式音频流封装说明）；12：wav；14：m4a（每个分片须是一个完整的 m4a 音频）；16：aac
        map.put("voice_format" , voiceFormat);
        map.put("voice_id" , "1111111111111111");// 16位 String 串作为每个音频的唯一标识，用户自己生成
        // 签名原文
        String url = "asr.cloud.tencent.com/asr/v2/"+APP_ID+"?" + initParamMap(map);
        System.err.println("签名原文：" + url);
        // 使用 SecretKey 进行 HmacSha1 加密
        String sign =genHMAC(url , SECRET_KEY ) ;
        System.err.println("签名sign：" + sign);
        url = "wss://" + url  + "&signature=" + URLEncoder.encode(sign);
        System.err.println(url);
        return RespBody.data(url);

    }
//
//    public static void main(String[] args) {
//
//    }
//    public static void main(String[] args) {
//        // 官网文档 https://cloud.tencent.com/document/product/1093/48982
//        Map<String , Object> params = new HashMap<>();
//        params.put("secretid" , SECRET_ID);//腾讯云注册账号的密钥 SecretId
//        Long nowTime = System.currentTimeMillis()/1000;
//        params.put("timestamp" , nowTime+"");// 当前 UNIX 时间戳，单位为秒。如果与当前时间相差过大，会引起签名过期错误
//        params.put("expired" , nowTime + 30*24*60 + "");// 签名的有效期截止时间 UNIX 时间戳，单位为秒。expired 必须大于 timestamp 且 expired - timestamp 小于90天
//        params.put("nonce" , "123456");// 随机正整数。用户需自行生成，最长10位
//        params.put("engine_model_type" , "16k_zh");
//        params.put("voice_id" , "AAAAAAAAAAAAAAAA");// 16位 String 串作为每个音频的唯一标识，用户自己生成
//        System.err.println();
//        // 签名原文
//        String url = "asr.cloud.tencent.com/asr/v2/1259228442?engine_model_type=16k_zh&expired=1592380492&filter_dirty=1&filter_modal=1&filter_punc=1&needvad=1&nonce=1592294092123&secretid=AKIDoQq1zhZMN8dv0psmvud6OUKuGPO7pu0r&timestamp=1592294092&voice_format=1&voice_id=RnKu9FODFHK5FPpsrN";
//        System.err.println("签名原文：" + url);
//        // 使用 SecretKey 进行 HmacSha1 加密，之后再进行 base64 编码
//        String sign = genHMAC(url , "kFpwoX5RYQ2SkqpeHgqmSzHK7h3A2fni" ) ;
//        System.err.println("签名sign：" + sign);
////        sign = Base64.encodeBase64String(sign.getBytes(StandardCharsets.UTF_8));
////        System.err.println("签名sign base64：" + sign);
//        url = "wss://" + url  + "&signature=" + URLEncoder.encode(sign);
//        System.err.println(url);
//    }


    public static String genHMAC(String data, String key) {
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance("HmacSHA1");
            //用给定密钥初始化 Mac 对象
            mac.init(signinKey);
            //完成 Mac 操作
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = Base64.getEncoder().encode(rawHmac);

        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }
    }

    /**
     * 处理输入参数
     * @param map
     * @return
     */
    public static String initParamMap(Map<String,Object> map){
        //构造签名参数集合
        Map<String,Object> signParamMap=new HashMap<>();
        if(map!=null && !map.isEmpty()){
            for(String key:map.keySet()){
                // 筛选 非null且非空的参数参与签名
                if(map.get(key)!=null && Strings.isNotEmpty(map.get(key).toString())){
                    signParamMap.put(key,map.get(key));
                }
            }
        }

        //排序 signParamMap的key值按照ASCII码升序排列
        TreeMap<String,Object> treeMap=new TreeMap();
        for(String key:signParamMap.keySet()){
            treeMap.put(key,signParamMap.get(key));
        }

        //拼接 将排序后的参数与其值，组合成“参数=参数值”的格式，并且把这些参数用&字符连接来，此时生成的字符串为待签名字符串
        StringBuilder sb=new StringBuilder();
        for(String key:treeMap.keySet()){
            sb.append(key+"="+treeMap.get(key)+"&");
        }
        sb=new StringBuilder(sb.toString().substring(0,sb.lastIndexOf("&")));
        return sb.toString();
    }


    /**
     * 文件转base64
     *
     * @param filePath
     * @return
     */
    public static String encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
//
//    public static void main(String[] args) {
//        System.err.println(encryptToBase64("C:\\Users\\86159\\Desktop\\testly.mp3"));
//    }




}
