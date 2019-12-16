package org.service.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "springcloud-service-provider")
public interface ProviderDemoService {
@GetMapping("/provider-demo")
    public String providerDemo(String consumerPort);
}