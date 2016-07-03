package com.infinitechnic.horseracing.data.hkjc.entity.race.history;

import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceSummary;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Deprecated
@Entity(value = "race_result")
public class RaceResult extends RaceSummary {
    private List<ResultRecord> resultRecords;

    @Transient
    private Models<ResultRecord> resultRecordsWrapper;

    public RaceResult(Date date, Integer raceNo) {
        super(date, raceNo);
        this.resultRecords = new ArrayList<>();
        this.resultRecordsWrapper = new Models<>(resultRecords);
    }

    public Models<ResultRecord> getResultRecords() {
        return resultRecordsWrapper;
    }
}
