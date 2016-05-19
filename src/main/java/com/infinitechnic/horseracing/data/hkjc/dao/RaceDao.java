package com.infinitechnic.horseracing.data.hkjc.dao;


import com.infinitechnic.horseracing.data.hkjc.entity.race.history.Race;

public interface RaceDao {
    Race saveRace(Race race);
}
