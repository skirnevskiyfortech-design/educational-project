package org.example.lifecycle.config;

import org.example.lifecycle.bean.KindFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example.lifecycle")
public class LifeCycleConfiguration {

    @Bean
    public KindFactoryBean tuna() {
        return new KindFactoryBean("tuna");
    }

    @Bean
    public KindFactoryBean salmon() {
        return new KindFactoryBean("salmon");
    }
}
