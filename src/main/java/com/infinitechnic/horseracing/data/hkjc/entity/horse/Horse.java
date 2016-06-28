package com.infinitechnic.horseracing.data.hkjc.entity.horse;

import com.infinitechnic.horseracing.data.hkjc.entity.BaseEntity;
import com.infinitechnic.util.StringUtil;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "horse")
public class Horse extends BaseEntity<Horse> {
    //TODO: handle multilingual
    private String name;

    public Horse() {
        super();
        name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
