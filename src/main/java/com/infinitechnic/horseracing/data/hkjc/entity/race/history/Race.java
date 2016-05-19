package com.infinitechnic.horseracing.data.hkjc.entity.race.history;

import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(value = "race")
public class Race {
    private Date date;
    private Integer raceNo;
    private List<Result> results;

    @Transient
    private Models<Result> resultsWrapper;

    public Race(Date date, Integer raceNo) {
        super();
        this.date = date;
        this.raceNo = raceNo;
        this.results = new ArrayList<>();
        this.resultsWrapper = new Models<>(results);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(Integer raceNo) {
        this.raceNo = raceNo;
    }

    public Models<Result> getResults() {
        return resultsWrapper;
    }
}
