package org.example.lifecycle.bean;

public class Kind {

    private final String kindName;

    public Kind(String kindName) {
        this.kindName = kindName;
    }


    @Override
    public String toString() {
        return "Kind{" +
                "kindName='" + kindName + '\'' +
                '}';
    }
}
