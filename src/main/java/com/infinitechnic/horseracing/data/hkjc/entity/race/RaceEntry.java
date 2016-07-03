package com.infinitechnic.horseracing.data.hkjc.entity.race;

import com.infinitechnic.horseracing.data.hkjc.entity.BaseEntity;
import com.infinitechnic.horseracing.data.hkjc.entity.Status;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

public class RaceEntry {
    private Integer no;
    private Integer draw;   // 檔位
    private Horse horse;
    private Trainer trainer;
    private Integer weight; // 負磅
    private Integer overWeight; // 可能超磅
    private Integer horseWeight;    // 排位體重
    private Integer horseWeightDifference;   // 排位體重+/-
    private String bestTime;    // 最佳時間
    private Integer age;    // 馬齡
    private Integer weightForAge;   // 分齡讓磅
    private Integer rating; // 評分
    private Integer ratingDifference;   // 評分 +/-
    private Integer priority;   // 優
    private String gear;    // 配備
    private String remarks;
    private Status entryStatus;
    private Status finalStatus;
    private Status actualStatus;

    public RaceEntry() {
        super();
        no = null;
        draw = null;
        horse = null;
        trainer = null;
        weight = null;
        overWeight = null;
        horseWeight = null;
        horseWeightDifference = null;
        bestTime = null;
        age = null;
        weightForAge = null;
        rating = null;
        ratingDifference = null;
        priority = null;
        gear = null;
        remarks = null;
        entryStatus = null;
        finalStatus = null;
        actualStatus = null;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getOverWeight() {
        return overWeight;
    }

    public void setOverWeight(Integer overWeight) {
        this.overWeight = overWeight;
    }

    public Integer getHorseWeight() {
        return horseWeight;
    }

    public void setHorseWeight(Integer horseWeight) {
        this.horseWeight = horseWeight;
    }

    public Integer getHorseWeightDifference() {
        return horseWeightDifference;
    }

    public void setHorseWeightDifference(Integer horseWeightDifference) {
        this.horseWeightDifference = horseWeightDifference;
    }

    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeightForAge() {
        return weightForAge;
    }

    public void setWeightForAge(Integer weightForAge) {
        this.weightForAge = weightForAge;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRatingDifference() {
        return ratingDifference;
    }

    public void setRatingDifference(Integer ratingDifference) {
        this.ratingDifference = ratingDifference;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Status getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(Status entryStatus) {
        this.entryStatus = entryStatus;
    }

    public Status getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(Status finalStatus) {
        this.finalStatus = finalStatus;
    }

    public Status getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(Status actualStatus) {
        this.actualStatus = actualStatus;
    }
}
