package com.infinitechnic.horseracing.data.hkjc.entity.race.entry;

import com.infinitechnic.horseracing.data.hkjc.entity.Models;
import com.infinitechnic.horseracing.data.hkjc.entity.race.Race;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RaceEntry extends Race {
    private List<EntryRecord> entryRecords;

    @Transient
    private Models<EntryRecord> entryRecordsWrapper;

    public RaceEntry(Date date, Integer raceNo) {
        super(date, raceNo);
        this.entryRecords = new ArrayList<>();
        this.entryRecordsWrapper = new Models<>(entryRecords);
    }

    public Models<EntryRecord> getEntryRecords() {
        return entryRecordsWrapper;
    }
}
