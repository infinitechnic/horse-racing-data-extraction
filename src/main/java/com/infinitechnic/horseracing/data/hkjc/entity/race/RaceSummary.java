package com.infinitechnic.horseracing.data.hkjc.entity.race;

import java.util.Date;

@Deprecated
public abstract class RaceSummary {//extends BaseEntity {
    protected Date date;
    protected Integer raceNo;
    protected String raceClass;   //賽事班次
    protected Integer distance;   //途程
    protected String type;    //C=Cup Race P=Priority to Run R=Restricted S=Special Condition
    protected Integer section; //組別 (eg. 1)
    protected String track;   //賽道 (eg. AWT or TURF)
    protected String ratingRange; //評分幅度 (eg. 100-080)

    protected RaceSummary(Date date, Integer raceNo) {
        super();
        this.date = date;
        this.raceNo = raceNo;
        this.raceClass = null;
        this.distance = null;
        this.type = null;
        this.section = null;
        this.track = null;
        this.ratingRange = null;
    }

    protected RaceSummary(Date date) {
        this(date, null);
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
