/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.fit2cloud.selfService.sdk;

import com.fit2cloud.selfService.sdk.utils.LogUtil;
import com.fit2cloud.selfService.sdk.constans.ResultHolder;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

//import java.util.ArrayList;
import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;



public class UBbaClient {

    private static final HostnameVerifier PROMISCUOUS_VERIFIER = ( s, sslSession ) -> true;

//    public Object createRemedyCmbdEntry(JSONObject jsonObject)throws Exception{
//
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://localhost:8008/api/jwt/login");
//        // send the username and password
//        List<NameValuePair> nvps = new ArrayList<>();
//        nvps.add(new BasicNameValuePair("username", "Allen"));
//        nvps.add(new BasicNameValuePair("password", "password"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//        // make the call and print the token
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//        HttpEntity entity = response.getEntity();
//        JSONObject json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
//        return json;
//
//    }

    public JSONObject createRemedyCMDBEntry(JSONObject jsonObject, String X_AUTH_APIKEY, String url) {

        try {
            //采用绕过验证的方式处理https请求
            SSLContext sslcontext = createIgnoreVerifySSL();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey",X_AUTH_APIKEY);

            HttpEntity request = new HttpEntity(jsonObject, headers);

            RestTemplate restTemplate = new RestTemplate();

            JSONObject json = restTemplate.postForEntity(url, request, JSONObject.class, sslcontext).getBody();

            return json;

        }catch (Exception e){

            LogUtil.error(e.getMessage());

            return null;

        }
    }

    public JSONObject createRemedyChangeEntry(JSONObject jsonObject, String X_AUTH_APIKEY, String url) {

        try {
            //采用绕过验证的方式处理https请求
            SSLContext sslcontext = createIgnoreVerifySSL();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey",X_AUTH_APIKEY);

//            MultiValueMap<String, String> map = JSONObject.toJavaObject(jsonObject, MultiValueMap.class);
//
//            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            HttpEntity request = new HttpEntity(jsonObject, headers);

            RestTemplate restTemplate = new RestTemplate();

            JSONObject json = restTemplate.postForEntity(url, request, JSONObject.class, sslcontext).getBody();

            return json;


        }catch (Exception e){

            LogUtil.error(e.getMessage());

            return null;

        }
    }

    public JSONObject updateRemedyCMDBEntry(JSONObject jsonObject, String X_AUTH_APIKEY, String url) {

        try {

            //采用绕过验证的方式处理https请求
            SSLContext sslcontext = createIgnoreVerifySSL();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey",X_AUTH_APIKEY);

//            MultiValueMap<String, String> map = JSONObject.toJavaObject(jsonObject, MultiValueMap.class);
//
//            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            HttpEntity request = new HttpEntity(jsonObject, headers);

            RestTemplate restTemplate = new RestTemplate();

            JSONObject json = restTemplate.postForEntity(url, request, JSONObject.class, sslcontext).getBody();

            return json;

        }catch (Exception e){

            LogUtil.error(e.getMessage());

            return null;

        }
    }

    public static RestTemplate restTemplate() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpsURLConnection.setDefaultHostnameVerifier( PROMISCUOUS_VERIFIER );

        restTemplate.setRequestFactory( new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if(connection instanceof HttpsURLConnection ){
                    ((HttpsURLConnection) connection).setHostnameVerifier(PROMISCUOUS_VERIFIER);
                }
                super.prepareConnection(connection, httpMethod);
            }
        });

        return restTemplate;
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

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

}
