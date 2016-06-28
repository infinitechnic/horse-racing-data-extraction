package com.infinitechnic.horseracing.data.hkjc.dao.race;

import com.infinitechnic.horseracing.data.hkjc.dao.BaseDao;
import com.infinitechnic.horseracing.data.hkjc.entity.BaseEntity;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.util.DateUtil;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class RaceDayDaoImpl extends BaseDao implements RaceDayDao {
    @Override
    public RaceDay saveRaceDay(RaceDay raceDay) {
        datastore.save(raceDay);
        return raceDay;
    }

    @Override
    public RaceDay findRaceDay(Date date) {
        return datastore.find(RaceDay.class, "_id", DateUtil.format(date, BaseEntity.FORMAT_DATE)).get();
    }
}
