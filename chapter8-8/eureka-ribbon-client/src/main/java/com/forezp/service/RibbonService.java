package com.forezp.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fangzhipeng on 2017/6/13.
 */
@Service
public class RibbonService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError",
            commandProperties = {@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "60000"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1")})
    public String hi(String name) {
        return restTemplate.getForObject("http://eureka-client/hi?name="+name,String.class);
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
