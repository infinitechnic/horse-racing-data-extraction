package com.infinitechnic.horseracing.data.hkjc.entity.race;

import com.infinitechnic.horseracing.data.hkjc.entity.BaseEntity;
import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import com.infinitechnic.horseracing.data.hkjc.exception.EntityManipulationException;
import com.infinitechnic.util.DateUtil;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity(value = "RaceDay")
public class RaceDay extends BaseEntity<RaceDay> {
    private String date;
    private String venue;
    private Integer meetingNo;    //賽馬日
    private String dayNight;
    private String track;  //賽道

    @Embedded
    private List<Race> races;
    @Transient
    private Models<Race> racesWrapper;

    public RaceDay() {
        super();
        this.date = null;
        this.venue = null;
        this.meetingNo = null;
        this.dayNight = null;
        this.track = null;
        this.races = null;
        this.racesWrapper = null;
    }

    public RaceDay(String date, String venue) {
        this();
        setDate(date);
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (!DateUtil.isDate(date, FORMAT_DATE)) {
            throw new EntityManipulationException(String.format("Invalid date format. [Value=%s, Format=%s]", date, FORMAT_DATE));
        }
        this.date = date;
        setId(date);
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getMeetingNo() {
        return meetingNo;
    }

    public void setMeetingNo(Integer meetingNo) {
        this.meetingNo = meetingNo;
    }

    public String getDayNight() {
        return dayNight;
    }

    public void setDayNight(String dayNight) {
        this.dayNight = dayNight;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public Models<Race> getRaces() {
        if (racesWrapper == null) {
            if (races == null) {
                races = new ArrayList<>();
            }
            racesWrapper = new Models<>(races);
        }
        return racesWrapper;
    }
}
