package com.infinitechnic.horseracing.data.hkjc.entity.race;

import java.util.Date;

public abstract class Race {
    private Date date;
    private Integer raceNo;

    protected Race(Date date, Integer raceNo) {
        super();
        this.date = date;
        this.raceNo = raceNo;
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
}
