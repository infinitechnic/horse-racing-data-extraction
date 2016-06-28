package com.infinitechnic.horseracing.data.hkjc.entity.race;

import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.List;

public class Race {
    private Integer raceNo;
    private String type;    //C=Cup Race P=Priority to Run R=Restricted S=Special Condition

    private String name;    //賽事名稱
    private String raceClass;   //賽事班次
    private String track;   //賽道 (eg. AWT or TURF)
    private String course;  //跑道
    private Integer distance;   //途程
    private Integer section; //組別/分組/編組 (eg. 1)
    private String ratingRange; //評分幅度 (eg. 100-080)

    @Embedded
    private List<RaceEntry> raceEntries;
    @Transient
    private Models<RaceEntry> raceEntriesWrapper;

    public Race(Integer raceNo) {
        super();
        this.raceNo = raceNo;
        this.raceClass = null;
        this.name = null;
        this.course = null;
        this.distance = null;
        this.type = null;
        this.section = null;
        this.track = null;
        this.ratingRange = null;
        this.raceEntries = null;
        this.raceEntriesWrapper = null;
    }

    public Race() {
        this(null);
    }

    public Integer getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(Integer raceNo) {
        this.raceNo = raceNo;
    }

    public String getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(String raceClass) {
        this.raceClass = raceClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    public Models<RaceEntry> getRaceEntries() {
        if (raceEntriesWrapper == null) {
            if (raceEntries == null) {
                raceEntries = new ArrayList<>();
            }
            raceEntriesWrapper = new Models<>(raceEntries);
        }
        return raceEntriesWrapper;
    }
}
