package org.service.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderDemoController{
    // 读取application.yml配置文件中的端口号，以方便查看实际执行来源yi
    @Value("${server.port}")
    private Integer port;

    @GetMapping("/provider-demo")
    public String providerDemo(String consumerPort) {
        return "provide from port " + port + " to " + consumerPort;
    }
}