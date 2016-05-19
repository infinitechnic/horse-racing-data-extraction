package com.infinitechnic.horseracing.data.hkjc.entity.race.history;

import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.jockey.Jockey;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private String place;
    private String horseNo;
    private Horse horse;
    private Jockey jockey;
    private Trainer trainer;
    private String actualWeight;
    private String horseWeight;
    private String draw;
    private String lbw;
    private List<Integer> runningPositions;
    @Transient
    private Models<Integer> runningPositionsWrapper;
    private String finishTime;
    private String winOdds;

    public Result() {
        super();
        place = null;
        horseNo = null;
        horse = null;
        jockey = null;
        trainer = null;
        actualWeight = null;
        horseWeight = null;
        draw = null;
        lbw = null;
        runningPositions = new ArrayList<>();
        runningPositionsWrapper = new Models<>(runningPositions);
        finishTime = null;
        winOdds = null;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getHorseNo() {
        return horseNo;
    }

    public void setHorseNo(String horseNo) {
        this.horseNo = horseNo;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Jockey getJockey() {
        return jockey;
    }

    public void setJockey(Jockey jockey) {
        this.jockey = jockey;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(String actualWeight) {
        this.actualWeight = actualWeight;
    }

    public String getHorseWeight() {
        return horseWeight;
    }

    public void setHorseWeight(String horseWeight) {
        this.horseWeight = horseWeight;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getLbw() {
        return lbw;
    }

    public void setLbw(String lbw) {
        this.lbw = lbw;
    }

    public Models<Integer> getRunningPositions() {
        return runningPositionsWrapper;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getWinOdds() {
        return winOdds;
    }

    public void setWinOdds(String winOdds) {
        this.winOdds = winOdds;
    }
}
