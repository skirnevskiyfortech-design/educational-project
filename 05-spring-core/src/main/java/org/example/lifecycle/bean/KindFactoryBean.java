package org.example.lifecycle.bean;

import org.springframework.beans.factory.FactoryBean;

public class KindFactoryBean implements FactoryBean<Kind> {

    private final String kind;

    public KindFactoryBean(String kind) {
        this.kind = kind;
    }

    @Override
    public Class<?> getObjectType() {
        return Kind.class;
    }

    @Override
    public Kind getObject() throws Exception {
        return new Kind(kind);
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
