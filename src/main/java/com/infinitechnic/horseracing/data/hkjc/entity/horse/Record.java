package com.infinitechnic.horseracing.data.hkjc.entity.horse;

import com.infinitechnic.horseracing.data.hkjc.entity.jockey.Jockey;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;

import java.math.BigDecimal;
import java.util.Date;

public class Record {
    private Date raceDate;
    private String raceIndex;   //場次 or 168
    private Integer raceNo;
    private String venue;
    private Integer placing;  //名次
    private String track;   //跑道
    private String course;  //賽道
    private Integer distance;
    private String going;   //場地狀況
    private String raceClass;   //賽事班次
    private Integer draw;   //檔位
    private Integer rating; //評分
    private Trainer trainer;
    private Jockey jockey;
    private String lengthBehindWinner;
    private Double winOdds; //獨贏賠率
    private Integer actualWeight;   //實際負磅
    private Integer[] runningPositions; //沿途走位
    private String finishTime;  //完成時間
    private Integer declaredHorseWeight;    //排位體重
    private String gear;    //配備

    public Record() {
        super();
        raceDate = null;
        raceIndex = null;
        raceNo = null;
        venue = null;
        placing = null;
        track = null;
        course = null;
        distance = null;
        going = null;
        raceClass = null;
        draw = null;
        rating = null;
        trainer = null;
        jockey = null;
        lengthBehindWinner = null;
        winOdds = null;
        actualWeight = null;
        runningPositions = null;
        finishTime = null;
        declaredHorseWeight = null;
        gear = null;
    }

    public Date getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(Date raceDate) {
        this.raceDate = raceDate;
    }

    public String getRaceIndex() {
        return raceIndex;
    }

    public void setRaceIndex(String raceIndex) {
        this.raceIndex = raceIndex;
    }

    public Integer getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(Integer raceNo) {
        this.raceNo = raceNo;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getPlacing() {
        return placing;
    }

    public void setPlacing(Integer placing) {
        this.placing = placing;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
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

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public String getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(String raceClass) {
        this.raceClass = raceClass;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Jockey getJockey() {
        return jockey;
    }

    public void setJockey(Jockey jockey) {
        this.jockey = jockey;
    }

    public String getLengthBehindWinner() {
        return lengthBehindWinner;
    }

    public void setLengthBehindWinner(String lengthBehindWinner) {
        this.lengthBehindWinner = lengthBehindWinner;
    }

    public Double getWinOdds() {
        return winOdds;
    }

    public void setWinOdds(Double winOdds) {
        this.winOdds = winOdds;
    }

    public Integer getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Integer actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Integer[] getRunningPositions() {
        return runningPositions;
    }

    public void setRunningPositions(Integer[] runningPositions) {
        this.runningPositions = runningPositions;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getDeclaredHorseWeight() {
        return declaredHorseWeight;
    }

    public void setDeclaredHorseWeight(Integer declaredHorseWeight) {
        this.declaredHorseWeight = declaredHorseWeight;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }
}
