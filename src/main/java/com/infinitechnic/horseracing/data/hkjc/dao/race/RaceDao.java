package com.infinitechnic.horseracing.data.hkjc.dao.race;


import com.infinitechnic.horseracing.data.hkjc.entity.race.history.RaceResult;

@Deprecated
public interface RaceDao {
    RaceResult saveRaceResult(RaceResult race);
}
