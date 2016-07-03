package com.infinitechnic.horseracing.data.hkjc.entity.race.fixture.calendar;

import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDaySummary;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.Date;

@Deprecated
@Entity(value = "race_day")
public class RaceDay extends RaceDaySummary {
//    private List<Race> raceList;
    private Models<RaceEntry> raceEntries;

    public RaceDay(Date date, String venue) {
        super(date, venue);
//        this.raceList = new ArrayList<>();
        this.raceEntries = new Models<>(new ArrayList<>());
    }

    public Models<RaceEntry> getRaceEntries() {
        return raceEntries;
    }
}
