package com.netease.course.neteasecourse.API签名与验签;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * API签名&验签思路：
 *  第三方会有自己的 accessKey 和 accessSecret,accessKey用来表示第三方的唯一编号，accessSecret用来生成签名sign的密钥
 *
 *  第三方签名：
 *      初始请求 -> http://localhost:8080?name=daituo&age=18&timestamp=1234567789&accessKey=SONGOUSONG
 *      对请求params使用accessSecret进行签名，生成签名sign后的请求 -> http://localhost:8080?name=daituo&age=18&timestamp=1234567789&accessKey=SONGOUSON34&sign=XNOSOUWWOENGAOUSNGOA
 *
 *  后台服务验签：
 *      解析戴签名sign的请求，获取请求参数params
 *      根据传入的accessKey，获取后台服务存储的accessSecret
 *      重新对请求params使用accessSecret进行签名，得到新的sign,然后对比传入的sign,相等则表明验证成功
 *
 *  注意：
 *      accessSecret签名密钥不能在URL传递，不安全
 *      第三方使用的签名算法需要与后台服务的签名算法保持一致
 *
 *
 * @Author daituo
 * @Date
 **/
public class ApiSignInterceptor extends HandlerInterceptorAdapter {

    // 签名超时时长，默认时间为5分钟，ms
    private static final int SIGN_EXPIRED_TIME = 5 * 60 * 1000;

    //private static final String API_SIGN_KEY_CONFIG_PATH = "/mop/common/system/api_sign_key_mapping.properties";

    private static final String SIGN_KEY = "sign";

    private static final String TIMESTAMP_KEY = "timestamp";

    private static final String ACCESS_KEY = "accessKey";

    private static final String ACCESS_SECRET = "accessSecret";

    /**
     * map存放服务端保存的accessKey和accessSecret
     */
    private static Map<String, String> map = new ConcurrentHashMap<String, String>();


    static {
        // 从zk加载key映射到内存里面
        //String data = ZKClient.get().getStringData(API_SIGN_KEY_CONFIG_PATH);
        Properties properties = new Properties();
        //properties.load(new StringReader(data));
        for (Object key : properties.keySet()) {
            map.put(String.valueOf(key), properties.getProperty(String.valueOf(key)));
        }

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String, Object> result = new HashMap<>();
        String timestamp = request.getParameter(TIMESTAMP_KEY);
        String accessKey = request.getParameter(ACCESS_KEY);

        //1.校验请求来源
        if (StringUtils.isEmpty(accessKey) || !accessKey.equals(map.get(ACCESS_KEY))) {
            result.put("code", 1004);
            result.put("msg", "无效的accessKey");
            return false;
        }
        //根据请求传递的accessKey，获取服务端存储的accessSecret
        String accessSecret = map.get(accessKey);

        //2.检查KEY是否合理(不是必须的)
        if (StringUtils.isEmpty(accessSecret)) {
            result.put("code", 1001);
            result.put("msg", "加密KEY不合法");
            //WebUtils.writeJsonByObj(result, response, request);
            return false;
        }

        //3.校验时间戳
        if (!StringUtils.isNumeric(timestamp)) {
            result.put("code", 1000);
            result.put("msg", "请求时间戳不合法");
            //WebUtils.writeJsonByObj(result, response, request);
            return false;
        }

        //4.校验请求的时效性
        Long ts = Long.valueOf(timestamp);
        if (System.currentTimeMillis() - ts > SIGN_EXPIRED_TIME) {
            result.put("code", 1002);
            result.put("msg", "请求超时");
            //WebUtils.writeJsonByObj(result, response, request);
            return false;
        }

        if (!verificationSign(request, accessSecret)) {
            result.put("code", 1003);
            result.put("msg", "签名错误");
            //WebUtils.writeJsonByObj(result, response, request);
            return false;
        }
        return true;
    }

    /**
     * 验证
     */
    private boolean verificationSign(HttpServletRequest request, String accessSecret) throws UnsupportedEncodingException {
        Enumeration<?> pNames = request.getParameterNames();
        Map<String, Object> params = new HashMap<>();
        while (pNames.hasMoreElements()) {
            String pName = (String) pNames.nextElement();
            if (SIGN_KEY.equals(pName)) {
                continue;
            }
            Object pValue = request.getParameter(pName);
            params.put(pName, pValue);
        }
        //获取请求传递的sign签名
        String originSign = request.getParameter(SIGN_KEY);
        //服务端重新生成签名，校验是否与请求传递的签名一致
        String sign = createSign(params, accessSecret);
        return sign.equals(originSign);
    }

    /**
     * 生成签名
     */
    private String createSign(Map<String, Object> params, String accessSecret) {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuilder temp = new StringBuilder();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            temp.append(valueString);
        }
        temp.append("&").append(ACCESS_SECRET).append("=").append(accessSecret);
        return Md5Utils.getMD5(temp.toString()).toUpperCase();  //MD5生成签名sing
    }
}
