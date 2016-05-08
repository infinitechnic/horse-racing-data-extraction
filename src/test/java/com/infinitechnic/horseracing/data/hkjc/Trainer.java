package com.infinitechnic.horseracing.data.hkjc;

public class Trainer {
    private String id;
    //TODO: handle multilingual
    private String name;

    public Trainer() {
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
