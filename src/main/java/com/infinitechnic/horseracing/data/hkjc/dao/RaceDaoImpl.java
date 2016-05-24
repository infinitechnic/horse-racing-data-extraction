package com.infinitechnic.horseracing.data.hkjc.dao;

import com.infinitechnic.horseracing.data.hkjc.entity.race.history.RaceResult;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RaceDaoImpl implements RaceDao {
    @Autowired
    private Datastore datastore;

    @Override
    public RaceResult saveRaceResult(RaceResult race) {
        datastore.save(race);
        return race;
    }
}
