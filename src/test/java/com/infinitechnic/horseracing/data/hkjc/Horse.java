package com.infinitechnic.horseracing.data.hkjc;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "horse")
public class Horse {
    @Id
    private String id;
    //TODO: handle multilingual
    private String name;

    public Horse() {
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
