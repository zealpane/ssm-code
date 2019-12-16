package org.service.demo.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignConsumerDemoController {
    
    @Value("${server.port}")
    private String port;
    @Autowired
    ProviderDemoService providerDemoService;

    @GetMapping("/provider-demo")
    public String hello() {
        return providerDemoService.providerDemo(port);
    }
}