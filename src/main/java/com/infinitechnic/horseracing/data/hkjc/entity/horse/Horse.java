package com.infinitechnic.horseracing.data.hkjc.entity.horse;

import com.infinitechnic.horseracing.data.hkjc.entity.BaseEntity;
import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import com.infinitechnic.horseracing.data.hkjc.entity.race.Race;
import com.infinitechnic.util.StringUtil;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.List;

@Entity(value = "horse")
public class Horse extends BaseEntity<Horse> {
    //TODO: handle multilingual
    private String name;

    @Embedded
    private List<Record> records;
    @Transient
    private Models<Record> recordsWrapper;

    public Horse() {
        super();
        this.name = null;
        this.records = null;
        this.recordsWrapper = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Models<Record> getRecords() {
        if (recordsWrapper == null) {
            if (records == null) {
                records = new ArrayList<>();
            }
            recordsWrapper = new Models<>(records);
        }
        return recordsWrapper;
    }
}
