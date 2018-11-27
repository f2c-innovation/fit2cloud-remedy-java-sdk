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

import com.alibaba.fastjson.JSONObject;
import com.fit2cloud.selfService.sdk.utils.LogUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;

public class UBbaClient {

    private static final HostnameVerifier PROMISCUOUS_VERIFIER = (s, sslSession) -> true;

    public JSONObject createRemedyCMDBEntry(JSONObject jsonObject, String X_AUTH_APIKEY, String url) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey", X_AUTH_APIKEY);

            HttpEntity request = new HttpEntity(jsonObject, headers);

            RestTemplate restTemplate = restTemplate();

            JSONObject json = restTemplate.postForEntity(url, request, JSONObject.class).getBody();

            return json;

        } catch (Exception e) {

            LogUtil.error(e.getMessage());

            return null;

        }
    }

    public JSONObject createRemedyChangeEntry(JSONObject jsonObject, String X_AUTH_APIKEY, String url) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey", X_AUTH_APIKEY);

            HttpEntity request = new HttpEntity(jsonObject, headers);

            RestTemplate restTemplate = restTemplate();

            JSONObject json = restTemplate.postForEntity(url, request, JSONObject.class).getBody();

            return json;


        } catch (Exception e) {

            LogUtil.error(e.getMessage());

            return null;

        }
    }

    public JSONObject updateRemedyCMDBEntry(JSONObject jsonObject, String X_AUTH_APIKEY, String url) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x_auth_apikey", X_AUTH_APIKEY);

//            MultiValueMap<String, String> map = JSONObject.toJavaObject(jsonObject, MultiValueMap.class);
//
//            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            HttpEntity request = new HttpEntity(jsonObject, headers);

            RestTemplate restTemplate = restTemplate();

            JSONObject json = restTemplate.postForEntity(url, request, JSONObject.class).getBody();

            return json;

        } catch (Exception e) {

            LogUtil.error(e.getMessage());

            return null;

        }
    }

    public static RestTemplate restTemplate() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpsURLConnection.setDefaultHostnameVerifier(PROMISCUOUS_VERIFIER);

        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setHostnameVerifier(PROMISCUOUS_VERIFIER);
                }
                super.prepareConnection(connection, httpMethod);
            }
        });

        return restTemplate;
    }

}
