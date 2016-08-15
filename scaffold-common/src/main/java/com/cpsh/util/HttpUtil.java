package com.cpsh.util;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public class HttpUtil {

    private static PoolingHttpClientConnectionManager cm = null;

    private static CloseableHttpClient httpClient = null;

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .build();

        httpClient = HttpClients.custom().setConnectionManager(cm)
                .setDefaultRequestConfig(globalConfig)
                .build();

    }

    public static String get(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    response=null;
                }
            }
        }

        return null;
    }

    public static String post(String url,HashMap<String, String> param){
        CloseableHttpResponse response = null;
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if(null!=param && param.size()>0){
            Set<String> keySet = param.keySet();
            for (String key : keySet) {
                String value = param.get(key);
                formparams.add(new BasicNameValuePair(key, value));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);

        try {
            response = httpClient.execute(httppost);
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        return null;
    }

    public static void main(String[] args) {
        String str = HttpUtil.get(
                "http://192.168.2.80/51auto/website/tree/master");
        System.out.println(str);
    }



}
