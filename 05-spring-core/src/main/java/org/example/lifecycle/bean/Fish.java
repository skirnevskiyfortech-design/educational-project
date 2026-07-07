package org.example.lifecycle.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Fish {

    private final String name;

    public Fish(String name) {
        this.name = name;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("PostConstruct: Fish name  = " + name);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("PreDestroy: Fish name  = " + name);
    }

    @Override
    public String toString() {
        return "Fish{" +
                "name='" + name + '\'' +
                '}';
    }
}
