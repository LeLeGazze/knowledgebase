package com.castle.fortress.admin.core.wrapper;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import org.apache.logging.log4j.util.Strings;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * http	请求包装类
 * @author Dawn
 */
public class BodyReaderRequestWrapper extends HttpServletRequestWrapper {
    private String body;
		private Map<String , String[]> parameterMap;

    public BodyReaderRequestWrapper(HttpServletRequest request) {
        super(request);
		    //请求路径
		    String uri = request.getServletPath();
		    boolean xssFlag = isWhiteList(uri);
		    parameterMap=new HashMap(request.getParameterMap());
        if(parameterMap!=null){
		        if(!xssFlag ){
				        for(String key:parameterMap.keySet()){
						        if(parameterMap.get(key)!=null && parameterMap.get(key).length>0){
								        String[] s = parameterMap.get(key);
								        s[0] = cleanXSS(parameterMap.get(key)[0]);
								        parameterMap.put(key,s);
						        }
				        }
		        }
        }
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        body = stringBuilder.toString();
		    if(!xssFlag && StrUtil.isNotEmpty(body)){
		    		body = cleanXSS(body);
		    }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
            }
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

		@Override
		public String getParameter(String name) {
				String[] results = parameterMap.get(name);
				return results==null?null:results[0];
		 }

		@Override
		public String[] getParameterValues(String name) {
				String[] parameters = parameterMap.get(name);
				if (parameters == null || parameters.length == 0) {
						return null;
				}
				return parameters;
		}

		@Override
		public Map<String,String[]> getParameterMap() {
				return parameterMap;
		}

		@Override
		public String getHeader(String name) {
				String value = super.getHeader(name);
				if (StrUtil.isNotBlank(value)) {
						value = cleanXSS(value);
				}
				return value;
		}

    public String getBody() {
        return this.body;
    }

	 /**
	 *对queryMap的值赋予新的值
	 * @param name
	 * @param value
	 */
	public void addQueryParameter(String name,Object value) {
		//analysisMap();
		if (parameterMap.containsKey(name)) {
			return;
		}
		if (value != null) {
			if (value instanceof String[]) {
					parameterMap.put(name, (String[]) value);
			} else if (value instanceof String) {
					parameterMap.put(name, new String[] {(String) value});
			} else {
					parameterMap.put(name, new String[] {String.valueOf(value)});
			}
		}

	}
	/**
	 *对bodyMap的值赋予新的值
	 * @param name
	 * @param value
	 */
	public void addBodyParamter(String name,Object value){
		Map paramBodyMap = new HashMap();
		if (!Strings.isEmpty(body)) {
			paramBodyMap = JSONObject.parseObject(body, Map.class);
		}
		paramBodyMap.put(name,value);
		body = JSONObject.toJSONString(paramBodyMap);
	}

	public Object queryApiParams(String name){
		return parameterMap.get(name)[0];
	}

		private static String cleanXSS(String value) {
			value = value.replaceAll("eval\\((.*)\\)", "");
			value = value.replaceAll("alert\\(.*\\)", "");
			value = value.replaceAll("(javascript|script)", "\"\"");
			value = value.replaceAll("(o|O)(n|N)(e|E)(r|R)(r|R)(o|O)(r|R).*?=.*?(\\\")[^\\\"]*(\\\")", "");
			value = value.replaceAll("(onafterprint|onbeforeprint|onbeforeonload" +
							"|onblur|onfocus|onhashchange|onload|onmessage" +
							"|onoffline|ononline|onpagehide|onpageshow" +
							"|onpopstate|onredo|onresize|onstorage" +
							"|onundo|onunload|onblur|onchange" +
							"|oncontextmenu|onfocus|onformchange|onforminput" +
							"|oninput|oninvalid|onreset|onselect|onsubmit" +
							"|onkeydown|onkeypress|onkeyup|onclick|ondblclick|ondrag" +
							"|ondragend|ondragenter|ondragleave|ondragover" +
							"|ondragstart|ondrop|onmousedown|onmousemove" +
							"|onmouseout|onmouseover|onmouseup|onmousewheel" +
							"|onscroll|onabort|oncanplay|oncanplaythrough" +
							"|ondurationchange|onemptied" +
							"|onended|onloadeddata|onloadedmetadataNew|onloadstartNew" +
							"|onpauseNew|onplayNew|onplayingNew|onprogressNew|onratechangeNew|onreadystatechangeNew" +
							"|onseekedNew|onseekingNew|onstalledNew" +
							"|onsuspendNew|ontimeupdateNew|onvolumechangeNew|onwaitingNew" +
							"|onshow|ontoggle).*?=.*?(\\\")[^\\\"]*(\\\")", "");//(?<=\").*?(?!\")
			value = value.replaceAll("(onafterprint|onbeforeprint|onbeforeonload" +
							"|onblur|onfocus|onhashchange|onload|onmessage" +
							"|onoffline|ononline|onpagehide|onpageshow" +
							"|onpopstate|onredo|onresize|onstorage" +
							"|onundo|onunload|onblur|onchange" +
							"|oncontextmenu|onfocus|onformchange|onforminput" +
							"|oninput|oninvalid|onreset|onselect|onsubmit" +
							"|onkeydown|onkeypress|onkeyup|onclick|ondblclick|ondrag" +
							"|ondragend|ondragenter|ondragleave|ondragover" +
							"|ondragstart|ondrop|onmousedown|onmousemove" +
							"|onmouseout|onmouseover|onmouseup|onmousewheel" +
							"|onscroll|onabort|oncanplay|oncanplaythrough" +
							"|ondurationchange|onemptied" +
							"|onended|onloadeddata|onloadedmetadataNew|onloadstartNew" +
							"|onpauseNew|onplayNew|onplayingNew|onprogressNew|onratechangeNew|onreadystatechangeNew" +
							"|onseekedNew|onseekingNew|onstalledNew" +
							"|onsuspendNew|ontimeupdateNew|onvolumechangeNew|onwaitingNew" +
							"|onshow|ontoggle).*?=.*?(\\\')[^\\\']*(\\\')", "");//(?<=\").*?(?!\")
			return value;
		}
		/**
		 * 校验是否是xss白名单
		 * @return
		 */
		public static boolean isWhiteList(String uri){
				String regexString="";
				for(String whiteUri: GlobalConstants.XSS_WHITE_LIST){
						regexString= whiteUri.replaceAll("\\*\\*","(.*)");
						if(Pattern.matches(regexString, uri)){
								return true;
						}
				}
				return false;
		}

}
