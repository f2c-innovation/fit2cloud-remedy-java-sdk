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
import org.springframework.web.client.RestTemplate;


/**
 * sdk管理控制器
 *
 * @author maguohao
 * @Date 2018年11月16日 12:55:31
 */
public class UBbaClient {


    /**
     * 创建token
     * @param jsonObject
     * @returnurl
     */
    public Object createToken(JSONObject jsonObject, String url) {

        RestTemplate restTemplate=new RestTemplate();

        JSONObject json = restTemplate.postForEntity(url, jsonObject, JSONObject.class).getBody();

        return  json;
    }

    /**
     * 释放token
     * @param jsonObject
     * @return
     */
    public Object releaseToken(JSONObject jsonObject, String url) {


        RestTemplate restTemplate=new RestTemplate();

        JSONObject json = restTemplate.postForEntity(url, jsonObject, JSONObject.class).getBody();

        return  json;
    }

    /**
     * 查询
     * @param jsonObject
     * @return
     */
    public Object queryResponse(JSONObject jsonObject, String url) {

        RestTemplate restTemplate=new RestTemplate();

        JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();

        return  json;
    }

    /**
     * 添加(关联task)
     * @param jsonObject
     * @return
     */
    public Object addTask(JSONObject jsonObject, String url) {

        RestTemplate restTemplate=new RestTemplate();

        JSONObject json = restTemplate.postForEntity(url,jsonObject, JSONObject.class).getBody();

        return  json;
    }

    /**
     * 添加(不关联task)
     * @param jsonObject
     * @return
     */
    public Object addNoTask(JSONObject jsonObject, String url) {

        RestTemplate restTemplate=new RestTemplate();

        JSONObject json = restTemplate.postForEntity(url,jsonObject, JSONObject.class).getBody();

        return  json;
    }

    /**
     * 修改
     * @param jsonObject
     * @return
     */
    public Object update(JSONObject jsonObject, String url) {

        RestTemplate restTemplate=new RestTemplate();

        JSONObject json = restTemplate.postForEntity(url,jsonObject, JSONObject.class).getBody();

        return  json;
    }

}
