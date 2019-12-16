package org.service.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerDemoController {
    
    @Value("${server.port}")
    private Integer port;
    
    @Autowired
	LoadBalancerClient loadBalancerClient;
    @GetMapping("/consumer-demo")
    public String consumerDemo() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("springcloud-service-provider");
        String url = serviceInstance.getUri() + "/providerDemo?consumerPort=" + port;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return "Invoke : " + url + ", return : " + result;
    }
}