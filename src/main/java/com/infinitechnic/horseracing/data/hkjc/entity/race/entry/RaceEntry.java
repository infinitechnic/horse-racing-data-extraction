package com.infinitechnic.horseracing.data.hkjc.entity.race.entry;

import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceSummary;

import java.util.ArrayList;
import java.util.Date;

@Deprecated
public class RaceEntry extends RaceSummary {
//    private List<EntryRecord> entryRecords;

//    @Transient
//    @JsonIgnore
//    private Models<EntryRecord> entryRecordsWrapper;
    private Models<EntryRecord> entryRecords;

    public RaceEntry(Date date, Integer raceNo) {
        super(date, raceNo);
//        this.entryRecords = new ArrayList<>();
//        this.entryRecordsWrapper = new Models<>(entryRecords);
        this.entryRecords = new Models<>(new ArrayList<>());
    }

    public Models<EntryRecord> getEntryRecords() {
        return entryRecords;
    }
}
