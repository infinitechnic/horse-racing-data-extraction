package com.infinitechnic.horseracing.data.hkjc.dao.race;


import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.entity.race.history.RaceResult;

import java.util.Date;

public interface RaceDayDao {
    RaceDay saveRaceDay(RaceDay raceDay);

    RaceDay findRaceDay(Date date);
}
