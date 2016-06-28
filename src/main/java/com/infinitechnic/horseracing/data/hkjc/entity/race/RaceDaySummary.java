package com.infinitechnic.horseracing.data.hkjc.entity.race;

import java.util.Date;

@Deprecated
public abstract class RaceDaySummary {//extends BaseEntity {
    protected Date date;
    protected String venue;
    protected Integer meetingNo;    //賽馬日
    protected String dayNight;
    protected String track;  //賽道

    protected RaceDaySummary(Date date, String venue) {
        super();
        this.date = date;
        this.venue = venue;
        this.meetingNo = null;
        this.dayNight = null;
        this.track = null;
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
}
