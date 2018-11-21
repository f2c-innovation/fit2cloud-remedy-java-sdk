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

//    public Object loginRemedyToken(){
//
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://localhost:8008/api/jwt/login");
//
//        // send the username and password
//        List<NameValuePair> nvps = new ArrayList<>();
//        nvps.add(new BasicNameValuePair("username", "Allen"));
//        nvps.add(new BasicNameValuePair("password", "password"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//
//        // make the call and print the token
//        HttpEntity entity = response.getEntity();
//        String token = EntityUtils.toString(entity, StandardCharsets.UTF_8);
//
//        return token;
//
//    }

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

    public Object createRemedyEntry(JSONObject jsonObject) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", jsonObject.getString("token"));
            headers.add("X-AR-Client-Type", jsonObject.getString("X-AR-Client-Type"));
            headers.add("X-AR-RPC-Queue", jsonObject.getString("X-AR-RPC-Queue"));

            MultiValueMap<String, String> map = JSONObject.toJavaObject(jsonObject, MultiValueMap.class);
            map.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            RestTemplate restTemplate = new RestTemplate();

            JSONObject json = restTemplate.postForEntity(jsonObject.getString("url"), request, JSONObject.class).getBody();

            return new ResultHolder(json);
        }catch (Exception e){
            LogUtil.error(e.getMessage());
            return new ResultHolder(false,e.getMessage());
        }
    }

    public Object updateRemedyEntry(JSONObject jsonObject) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", jsonObject.getString("token"));
            headers.add("X-AR-Client-Type", jsonObject.getString("X-AR-Client-Type"));
            headers.add("X-AR-RPC-Queue", jsonObject.getString("X-AR-RPC-Queue"));

            MultiValueMap<String, String> map = JSONObject.toJavaObject(jsonObject, MultiValueMap.class);
            map.add("Content-Type", "application/json");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            RestTemplate restTemplate = new RestTemplate();

            JSONObject json = restTemplate.postForEntity(jsonObject.getString("url"), request, JSONObject.class).getBody();

            return new ResultHolder(json);
        }catch (Exception e){
            LogUtil.error(e.getMessage());
            return new ResultHolder(false,e.getMessage());
        }
    }

}
