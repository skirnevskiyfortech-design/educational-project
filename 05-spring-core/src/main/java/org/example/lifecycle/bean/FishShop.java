package org.example.lifecycle.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class FishShop {

    private final ObjectProvider<Fish> fishShopProvide;

    private final Map<String, Kind> description;

    @PostConstruct
    public void openShop() {
        System.out.println(" FishShop OpenShop ");
    }

    public void works() {
        System.out.println(" FishShop Works ");
    }

    public void makeFish(String fishName) {
        Fish fishShop = fishShopProvide.getObject(fishName);
        System.out.println(" FishShop MakeFish " + fishShop);
    }

    public void makeFish(String fishName, String kind) {
        Fish fishShop = fishShopProvide.getObject(fishName);
        System.out.println(" FishShop MakeFish " + fishShop + " kind " + description.get(kind));
    }

    @PreDestroy
    public void closeShop() {
        System.out.println(" FishShop CloseShop ");
    }
}
