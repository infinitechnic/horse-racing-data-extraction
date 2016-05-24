package com.infinitechnic.horseracing.data.hkjc.dao;


import com.infinitechnic.horseracing.data.hkjc.entity.race.history.RaceResult;

public interface RaceDao {
    RaceResult saveRaceResult(RaceResult race);
}
