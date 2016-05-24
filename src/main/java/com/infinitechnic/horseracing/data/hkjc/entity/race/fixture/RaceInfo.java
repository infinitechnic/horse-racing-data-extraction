package com.infinitechnic.horseracing.data.hkjc.entity.race.fixture;

import com.infinitechnic.horseracing.data.hkjc.entity.race.Race;

import java.util.Date;

public class RaceInfo extends Race {
    private String raceClass;   //賽事班次
    private Integer distance;   //途程
    private String type;    //C=Cup Race P=Priority to Run R=Restricted S=Special Condition
    private Integer section; //組別 (eg. 1)
    private String track;   //賽道 (eg. AWT or TURF)
    private String ratingRange; //評分幅度 (eg. 100-080)

    public RaceInfo(Date date, Integer raceNo) {
        super(date, raceNo);
        this.raceClass = null;
        this.distance = null;
        this.type = null;
        this.section = null;
        this.track = null;
        this.ratingRange = null;
    }

    public String getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(String raceClass) {
        this.raceClass = raceClass;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getRatingRange() {
        return ratingRange;
    }

    public void setRatingRange(String ratingRange) {
        this.ratingRange = ratingRange;
    }
}
