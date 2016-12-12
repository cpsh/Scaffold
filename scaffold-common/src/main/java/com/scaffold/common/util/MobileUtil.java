package com.omniprimeinc.audit.platform.autojobs.services.utils;

import com.google.gson.Gson;
import com.omniprimeinc.component.common.utils.StringUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shichp on 2016/12/10.
 */
public class MobileUtil {
    private static final String MOBILE_URL = "http://sj.apidata.cn/";
    private static RestTemplate restTemplate = new RestTemplate();
    private MobileApiResponse mobileApiResponse;

    /**
     * @param mobile
     * @return
     */
    public  String getMobileInfo(String mobile) {

        String message = "";
        Integer status = 0;
        String data = "";
        String json = "";

        if (StringUtil.isEmpty(mobile)) {
            message = "手机号码为空";
            status = 0;
            mobileApiResponse = new MobileApiResponse();
            mobileApiResponse.setStatus(status);
            mobileApiResponse.setMessage(message);
            mobileApiResponse.setData(new MobileApiResponse().new MobileApiResponseData());
            json = new Gson().toJson(mobileApiResponse);
            return json;
        }
        boolean isVaild = ValidatorUtil.isMobile(mobile);
        if (isVaild) {
            String url = MOBILE_URL + "?mobile=" + mobile;
            try {
                Map<String, String> params = new LinkedHashMap<>();
                params.put("mobile", mobile);
                json = invokeRestTemplateMethod(restTemplate, MOBILE_URL, params, String.class, HttpMethod.GET);
                //   json = rest.getForObject(url,String.class);//最简单

                json = new String(json.getBytes("ISO-8859-1"), "UTF-8");//务必需要转码，接口方未限定格式
                return json;
            } catch (Exception e) {
                message = e.getMessage();
                status = 0;
                mobileApiResponse = new MobileApiResponse();
                mobileApiResponse.setStatus(status);
                mobileApiResponse.setMessage(message);
                mobileApiResponse.setData(new MobileApiResponse().new MobileApiResponseData());
                json = new Gson().toJson(mobileApiResponse);
            }
        } else {
            message = "手机号码格式不正确!";
            status = 0;
            mobileApiResponse = new MobileApiResponse();
            mobileApiResponse.setStatus(status);
            mobileApiResponse.setMessage(message);
            mobileApiResponse.setData(new MobileApiResponse().new MobileApiResponseData());
            json = new Gson().toJson(mobileApiResponse);
        }
        return json;
    }

    /**
     * 传递参数，进行restTemplate方法调用
     *
     * @param restTemplate
     * @param url
     * @param map
     * @param var
     * @param method       方法类型
     * @param <T>
     * @return
     */
    public static <T> T invokeRestTemplateMethod(RestTemplate restTemplate, String url, Map<String, T> map, Class<T> var, HttpMethod method) {
        /*
        *初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
        * 添加的StringHttpMessageConverter非UTF-8
        * 所以先要移除原有的StringHttpMessageConverter，
        * 再添加一个字符集为UTF-8的StringHttpMessageConvert
        * */
        /*
        FormHttpMessageConverter fc = new FormHttpMessageConverter();
        StringHttpMessageConverter s = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();
        partConverters.add(s);
        partConverters.add(new ResourceHttpMessageConverter());
        fc.setPartConverters(partConverters);
        restTemplate.setMessageConverters(Arrays.asList(fc,new MappingJackson2HttpMessageConverter()));
        */

        switch (method) {
            case POST:
                System.out.println(String.format("method : %s , url : %s", method.name(), url));
                return restTemplate.postForObject(url, map, var);
            case GET:
                /*
                //jdk8 ++
                String getParams = "?" + map.keySet().stream().map(k -> String.format("%s={%s}", k, k)).collect(Collectors.joining("&"));
                */
                String getParams = "?";
                StringBuffer buffer = new StringBuffer();
                if (map != null && map.size() > 0) {
                    for (String key : map.keySet()) {
                        buffer.append(key).append("=").append(map.get(key)).append("&");
                    }
                }
                if (StringUtil.isNotEmpty(buffer.toString())) {
                    getParams += buffer.toString();
                    getParams = getParams.substring(0, getParams.length() - 1);
                }
                url += getParams;
                System.out.println(String.format("method : %s , url : %s", method.name(), url));
                return restTemplate.getForObject(url, var, map);
            default:
                return restTemplate.postForObject(url, map, var);
        }
    }


    /**
     * 手动判断传入的参数号码为哪家运营商
     *
     * @param mobile
     * @return 运营商名称
     */
    public static String validateMobile(String mobile) {
        String returnString = "";
        if (mobile == null || mobile.trim().length() != 11) {
            return "-1";        //mobile参数为空或者手机号码长度不为11，错误！
        }
        if (mobile.trim().substring(0, 3).equals("134") || mobile.trim().substring(0, 3).equals("135") ||
                mobile.trim().substring(0, 3).equals("136") || mobile.trim().substring(0, 3).equals("137")
                || mobile.trim().substring(0, 3).equals("138") || mobile.trim().substring(0, 3).equals("139") || mobile.trim().substring(0, 3).equals("150") ||
                mobile.trim().substring(0, 3).equals("151") || mobile.trim().substring(0, 3).equals("152")
                || mobile.trim().substring(0, 3).equals("157") || mobile.trim().substring(0, 3).equals("158") || mobile.trim().substring(0, 3).equals("159")
                || mobile.trim().substring(0, 3).equals("187") || mobile.trim().substring(0, 3).equals("188")) {
            returnString = "1";   //中国移动
        }
        if (mobile.trim().substring(0, 3).equals("130") || mobile.trim().substring(0, 3).equals("131") ||
                mobile.trim().substring(0, 3).equals("132") || mobile.trim().substring(0, 3).equals("156")
                || mobile.trim().substring(0, 3).equals("185") || mobile.trim().substring(0, 3).equals("186")) {
            returnString = "2";   //中国联通
        }
        if (mobile.trim().substring(0, 3).equals("133") || mobile.trim().substring(0, 3).equals("153") ||
                mobile.trim().substring(0, 3).equals("180") || mobile.trim().substring(0, 3).equals("189")) {
            returnString = "3";   //中国电信
        }
        if (returnString.trim().equals("")) {
            returnString = "0";   //未知运营商
        }
        return returnString;
    }



    public static void main(String[] args) {
        String telePhone = "021-6312836";
        System.out.println(ValidatorUtil.isTelePhone(telePhone));

        MobileUtil util = new MobileUtil();
        String mobile = "15861331406";
        String result = util.getMobileInfo(mobile);
//        System.out.println(String.format("mobile : %s \nresult : %s", mobile, result));
        Gson gson = new Gson();
        MobileApiResponse response = gson.fromJson(result,MobileApiResponse.class);
        System.out.print(String.format("mobile : %s \ninfo : %s", mobile, gson.toJson(response)));
    }

}

