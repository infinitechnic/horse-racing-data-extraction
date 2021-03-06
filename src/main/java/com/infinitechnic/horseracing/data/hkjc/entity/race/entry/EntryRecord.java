package com.infinitechnic.horseracing.data.hkjc.entity.race.entry;

import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;
import com.infinitechnic.util.StringUtil;

@Deprecated
public class EntryRecord {
    private Boolean reserved;
    private Integer no;
    private Horse horse;
    private Trainer trainer;
    private Integer weight; // 負磅
    private Integer weightForAge;   // 分齡讓磅
    private Integer rating; // 評分
    private Integer ratingDifference;   // 評分 +/-
    private Integer priority;   // 優
    private String remarks;

    public EntryRecord() {
        super();
        reserved = null;
        no = null;
        horse = null;
        trainer = null;
        weight = null;
        weightForAge = null;
        rating = null;
        ratingDifference = null;
        priority = null;
        remarks = null;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
