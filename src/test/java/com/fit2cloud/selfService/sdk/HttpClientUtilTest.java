package com.fit2cloud.selfService.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpClientUtilTest {

    @Test
    public void doGet() {

        String url = "https://easp.bba:10443/2ndCMDB/v1/server/server_list?pageNumber=1&pageSize=10&server_name=lsktix0eb47165f&apiKey=riwp33yvqurom8t4n9vkou8hbadk8hf4uqtd7yrx";
        try {
            HttpClientUtil.doGet(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}