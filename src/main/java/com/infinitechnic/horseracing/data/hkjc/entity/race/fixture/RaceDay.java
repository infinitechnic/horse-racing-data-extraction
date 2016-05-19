package com.infinitechnic.horseracing.data.hkjc.entity.race.fixture;

import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(value = "race_day")
public class RaceDay {
    private Date date;
    private String venue;
    private String dayNight;
    private String course;  //賽道
    private List<RaceInfo> raceInfoList;

    @Transient
    private Models<RaceInfo> raceInfoListWrapper;

    public RaceDay() {
        super();
        this.date = null;
        this.venue = null;
        this.dayNight = null;
        this.course = null;
        this.raceInfoList = new ArrayList<>();
        this.raceInfoListWrapper = new Models<>(raceInfoList);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDayNight() {
        return dayNight;
    }

    public void setDayNight(String dayNight) {
        this.dayNight = dayNight;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Models<RaceInfo> getRaceInfoListWrapper() {
        return raceInfoListWrapper;
    }
}
