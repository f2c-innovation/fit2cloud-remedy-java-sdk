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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//import java.util.ArrayList;
//import java.util.List;
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


    public Object createRemedyToken(JSONObject jsonObject) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("username", jsonObject.getString("username"));
            map.add("password", jsonObject.getString("password"));

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            RestTemplate restTemplate = new RestTemplate();

            JSONObject json = restTemplate.postForEntity(jsonObject.getString("url"), request, JSONObject.class).getBody();

            return new ResultHolder(json);

        } catch (Exception e) {

            LogUtil.error(e.getMessage());
            return new ResultHolder(false, e.getMessage());

        }
    }

    public void releaseRemedyToken(JSONObject jsonObject) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("Authorization", jsonObject.getString("Authorization"));

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.postForEntity(jsonObject.getString("url"), request, String.class).getBody();

        } catch (Exception e) {

            LogUtil.error(e.getMessage());

        }

    }

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

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey",X_AUTH_APIKEY);

            MultiValueMap<String, String> map = JSONObject.toJavaObject(jsonObject, MultiValueMap.class);
            map.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            RestTemplate restTemplate = new RestTemplate();

            JSONObject json = restTemplate.postForEntity(url, request, JSONObject.class).getBody();

            return json;

        }catch (Exception e){

            LogUtil.error(e.getMessage());

            return null;

        }
    }

    public void createRemedyChangeEntry(JSONObject jsonObject, String X_AUTH_APIKEY, String url) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey",X_AUTH_APIKEY);

            MultiValueMap<String, String> map = JSONObject.toJavaObject(jsonObject, MultiValueMap.class);
            map.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.postForEntity(url, request, JSONObject.class).getBody();


        }catch (Exception e){

            LogUtil.error(e.getMessage());

        }
    }


    public void updateRemedyCMDBEntry(JSONObject jsonObject, String X_AUTH_APIKEY, String url) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey",X_AUTH_APIKEY);

            MultiValueMap<String, String> map = JSONObject.toJavaObject(jsonObject, MultiValueMap.class);
            map.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.postForEntity(url, request, JSONObject.class).getBody();


        }catch (Exception e){

            LogUtil.error(e.getMessage());

        }
    }

}
