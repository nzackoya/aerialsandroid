package com.aerials.network;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class RequestResponseBean {

    public static String HEADER_KEY = "";
    public static String HEADER_VALUE = "";

    HttpHeaders headers;

    public abstract MultiValueMap<String, String> getParameters();

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public abstract String getUrl();

    public HttpEntity<MultiValueMap<String, String>> getEntity(){
        return new HttpEntity<MultiValueMap<String, String>>(getParameters(), getHeaders());
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_RSS_XML));
        headers.set("Connection", "Close");
        headers.set(HEADER_KEY, HEADER_VALUE);
        return headers;
    }

    public String toString(){
        return getUrl();
    }

}
