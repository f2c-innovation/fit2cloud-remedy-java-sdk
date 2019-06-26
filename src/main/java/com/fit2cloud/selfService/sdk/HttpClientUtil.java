package com.fit2cloud.selfService.sdk;

import com.fit2cloud.selfService.sdk.utils.LogUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> param) throws Exception {

        // 创建Httpclient对象
//        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpClient = null;
        String resultString = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = createClient();

            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                throw e;
            }
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                throw e;
            }
        }
        return resultString;
    }

    public static String doGet(String url) throws Exception {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String resultString = null;
        try {
            httpClient = createClient();

            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                throw e;
            }
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                throw e;
            }
        }

        return resultString;
    }

    public static String doPost(String url) throws Exception {
        return doPost(url, null);
    }

    public static String doPostJson(String json, String url) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String resultString = null;
        try {
            httpClient = createClient();

            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setHeader("Content-Type", "application/json");
//            httpPost.setHeader("x_auth_apikey", X_AUTH_APIKEY);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                if(response.getEntity()!=null){
                    resultString = EntityUtils.toString(response.getEntity(), "utf-8");
                }
            }else if(response.getStatusLine().getStatusCode() == 500){
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
                throw new Exception("HTTP-Internal Server Error 500 Reason is : " + resultString);
            }else {
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
                throw new Exception("HTTP-Internal Server Error is : " + response.getStatusLine().getStatusCode() + " " + resultString);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                throw e;
            }
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                throw e;
            }
        }

        return resultString;
    }

    public static CloseableHttpClient createClient() throws Exception {
        //采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();
        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        //创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        return client;
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            //            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            //            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            //            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }
}

