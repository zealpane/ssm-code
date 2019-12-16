package hello.spring.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserBean.class)
public class StarterAutoConfigure {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "hello.spring.boot.starter", value = "enabled", havingValue = "true")
    UserBean starterService (){
        return new UserBean();
    }
}