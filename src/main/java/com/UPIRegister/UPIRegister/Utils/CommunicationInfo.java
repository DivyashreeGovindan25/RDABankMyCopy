package com.UPIRegister.UPIRegister.Utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@Component
public class CommunicationInfo {
    private HttpHeaders httpHeaders;
    private HttpEntity<Object> httpEntity;
    private RestTemplate restTemplate;
    public HttpHeaders getHttpHeadersSingleEntity(){
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
    public HttpEntity<Object> getHttpEntityForGet(){
        httpEntity = new HttpEntity<Object>(httpHeaders);
        return httpEntity;
    }
    public HttpEntity<Object> getHttpEntityForPost(Object obj){
        httpEntity = new HttpEntity<Object>(obj,httpHeaders);
        return httpEntity;
    }
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
