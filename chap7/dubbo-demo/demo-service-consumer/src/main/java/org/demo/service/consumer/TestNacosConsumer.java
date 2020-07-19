package org.demo.service.consumer;

import org.demo.common.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

@Controller
@RequestMapping("test")
public class TestNacosConsumer {
   @Reference
   DemoService demoService;
 
   @RequestMapping(value = "test", method = RequestMethod.GET)
   @ResponseBody
   public String getCounsumerTest() {
      return demoService.sayHello("段老师");
   }
}
