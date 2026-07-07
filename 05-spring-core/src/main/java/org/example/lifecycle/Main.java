package org.example.lifecycle;

import org.example.lifecycle.bean.FishShop;
import org.example.lifecycle.config.LifeCycleConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfiguration.class);
        FishShop fishShop = context.getBean(FishShop.class);
//        fishShop.works();
//        fishShop.makeFish("krytoi ribka");
//        fishShop.makeFish("krytoi ribka");
        fishShop.makeFish("krytoi ribka","salmon");
        fishShop.makeFish("krytoi ribka","tuna");
        fishShop.makeFish("krytoi ribka","non-existed");

        context.close();
    }
}
