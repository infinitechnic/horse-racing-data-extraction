package com.infinitechnic.horseracing.data.hkjc.entity.jockey;

public class Jockey {
    private String id;
    //TODO: handle multilingual
    private String name;

    public Jockey() {
        super();
        id = null;
        name = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
