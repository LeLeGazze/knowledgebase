package com.castle.fortress.admin.system.bindapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.system.bindapi.service.BindApiService;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.utils.SignUtils;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.utils.CommonUtil;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.*;

import java.net.URLEncoder;
import java.util.*;

/**
 * 绑定的腾讯云api服务类
 * @@author castle
 */

public class TencentBindApiClient implements BindApiService {
    private Credential cred;
    private String region;
    private ConfigApiDto platform;

    public TencentBindApiClient(ConfigApiDto platform,String region){
        this.platform=platform;
        this.cred = new Credential(platform.getParamMap().get("secretId").toString(), platform.getParamMap().get("secretKey").toString());
        this.region = region;
    }

    @Override
    public String[] keywordsExtraction(String platformCode, String text, Long num) {
        NlpClient nlpClient = new NlpClient(this.cred,this.region);
        KeywordsExtractionRequest req = new KeywordsExtractionRequest();
        //仅支持UTF-8格式，不超过10000字符
        if(text.length()>10000){
            text = text.substring(0,10000);
        }
        req.setText(text);
        req.setNum(num);
        try {
            KeywordsExtractionResponse resp = nlpClient.KeywordsExtraction(req);
            Keyword[] keywords = resp.getKeywords();
            if(keywords!=null){
                String[] keys = new String[keywords.length];
                for(int i=0;i<keywords.length;i++){
                    keys[i] = keywords[i].getWord();
                }
                return keys;
            }

        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return new String[0];

    }

    @Override
    public String autoSummarization(String platformCode, String text, Long Length) {
        NlpClient nlpClient = new NlpClient(this.cred,this.region);
        AutoSummarizationRequest req = new AutoSummarizationRequest();
        //仅支持UTF-8格式，不超过2000字
        if(text.length()>2000){
            text = text.substring(0,2000);
        }
        req.setText(text);
        req.setLength(Length);
        try {
            AutoSummarizationResponse resp = nlpClient.AutoSummarization(req);
            return resp.getSummary();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Map<String, Object> textCorrection(String platformCode, String text) {
        Map<String,Object> map = new HashMap<>();
        NlpClient nlpClient = new NlpClient(this.cred,this.region);
        Integer standardNum =128;
        //仅支持UTF-8格式，不超过128字符
        TextCorrectionProRequest req = new TextCorrectionProRequest();
        try {
            if(text.length()<=standardNum){
                req.setText(text);
                TextCorrectionProResponse resp=nlpClient.TextCorrectionPro(req);
                if(resp.getCCITokens()!=null){
                    List<Map> list = new ArrayList<>();
                    for(CCIToken cci :resp.getCCITokens()){
                        list.add(BeanUtil.beanToMap(cci));
                    }
                    map.put("CCITokens",list);
                }
                if(StrUtil.isNotEmpty(resp.getResultText())){
                    map.put("ResultText",resp.getResultText());
                }
            }else{
                int times = text.length()%standardNum==0?(text.length()/standardNum):((text.length()/standardNum)+1);
                for(int i=0;i<times;i++){
                    String t="";
                    //最后一段
                    if(i==(times-1)){
                        t= text.substring(i*standardNum);
                    }else{
                        t= text.substring(i*standardNum,(i+1)*standardNum);
                    }
                    req.setText(t);
                    TextCorrectionProResponse resp=nlpClient.TextCorrectionPro(req);
                    if(resp.getCCITokens()!=null){
                        List<Map> list = new ArrayList<>();
                        for(CCIToken cci :resp.getCCITokens()){
                            list.add(BeanUtil.beanToMap(cci));
                        }
                        if(map.get("CCITokens")==null){
                            map.put("CCITokens",list);
                        }else{
                            List<Map> l = (List<Map>)map.get("CCITokens");
                            l.addAll(list);
                            map.put("CCITokens",l);
                        }
                    }
                    if(StrUtil.isNotEmpty(resp.getResultText())){
                        if(map.get("ResultText")==null){
                            map.put("ResultText",resp.getResultText());
                        }else{
                            map.put("ResultText",map.get("ResultText").toString()+resp.getResultText());
                        }
                    }
                }
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> textAudit(String platformCode, String text, String detectType) {
        Map<String,Object> map = new HashMap<>();
        //请求地址
        String path ="/text/auditing";
        String url = "https://" + this.platform.getParamMap().get("bucketName")
                + "-"+this.platform.getParamMap().get("appId") +".ci."+this.platform.getParamMap().get("cosRegion")+".myqcloud.com"+path;

        Map<String,Map> bodyMap = new HashMap<>();
        Map<String,Map> request = new HashMap<>();
        Map<String , String> input = new HashMap<>();
        ;
        input.put("Content" , Base64.encode(text));
        Map<String , String> conf = new HashMap<>();
        conf.put("DetectType" , detectType);
        request.put("Input" , input);
        request.put("Conf" , conf);

        bodyMap.put("Request" , request);
        String body = XmlUtil.mapToXmlStr(bodyMap,true);
        body = body.replace("<xml>" , "").replace("</xml>" , "");
        // 该接口参数与返回 为xml格式
        System.out.println(body);
        String result = HttpRequest.post(url)
                .header("Authorization",getXMlAuthorization(HttpMethodName.POST,path))
                .header("Content-Type","application/xml")
                .body(body).execute().body();
        JSONObject jsonObject = JSONUtil.xmlToJson(result);
        if(jsonObject.getJSONObject("Error") != null){
            throw new BizException(jsonObject.getJSONObject("Error").getStr("Code"));
        }
        map = JSONUtil.toBean(jsonObject.getJSONObject("Response").getJSONObject("JobsDetail"),Map.class);
        return map;
    }

    @Override
    public String getAsrWs(String platformCode, String engineModelType, String voiceFormat) {
        Map<String , Object> map = new HashMap<>();
        //腾讯云注册账号的密钥 SecretId
        map.put("secretid" , this.platform.getParamMap().get("secretId"));
        Long nowTime = System.currentTimeMillis()/1000;
        // 当前 UNIX 时间戳，单位为秒。如果与当前时间相差过大，会引起签名过期错误
        map.put("timestamp" , nowTime+"");
        // 签名的有效期截止时间 UNIX 时间戳，单位为秒。expired 必须大于 timestamp 且 expired - timestamp 小于90天
        map.put("expired" , nowTime + 30*24*60 + "");
        // 随机正整数。用户需自行生成，最长10位
        map.put("nonce" , "123456");
//        map.put("engine_model_type" , "8k_zh");// 引擎模型类型 8k_zh：电话场景 8k 中文普通话通用，16k_zh：非电话场景 16k 中文普通话通用 类型比较多 详细看官网:https://cloud.tencent.com/document/product/1093/48982
        map.put("engine_model_type" ,engineModelType);
//        map.put("voice_format" , "8");// 语音编码方式，可选，默认值为4。1：pcm；4：speex(sp)；6：silk；8：mp3；10：opus（opus 格式音频流封装说明）；12：wav；14：m4a（每个分片须是一个完整的 m4a 音频）；16：aac
        map.put("voice_format" , voiceFormat);
        // 16位 String 串作为每个音频的唯一标识，用户自己生成
        map.put("voice_id" , CommonUtil.getRandomString(16,CommonUtil.RANGE1));
        // 签名原文
        String url = "asr.cloud.tencent.com/asr/v2/"+this.platform.getParamMap().get("appId")+"?" + SignUtils.initParamMap(map);
        System.err.println("签名原文：" + url);
        String sign = SignUtils.genHMAC(url , this.platform.getParamMap().get("secretKey").toString() );
        System.err.println("签名sign：" + sign);
        url = "wss://" + url  + "&signature=" + URLEncoder.encode(sign);
        System.err.println(url);
        return url;
    }

    /**
     * 获取腾讯云对象存储 COS 的 XML API签名
     * @return
     */
    private String getXMlAuthorization(HttpMethodName methodName , String resoucePath ){
        COSSigner cosSigner = new COSSigner();
        Map<String, String> headerMap   = new HashMap<>();
        Map<String, String> paramMap  = new HashMap<>();
        // 参数信息 appid等
        BasicCOSCredentials cred  = new BasicCOSCredentials(this.platform.getParamMap().get("secretId").toString(), this.platform.getParamMap().get("secretKey").toString());
        Date startTime  = new Date();// 签名有效开始时间
        Date expiredTime  = new Date(startTime.getTime() + 60*1000) ;// 签名有效结束时间
        String signStr =  cosSigner.buildAuthorizationStr(methodName,resoucePath,headerMap,paramMap,cred,startTime,expiredTime);
        return signStr;
    };
}