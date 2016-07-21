package com.infinitechnic.horseracing.data.hkjc.dao.horse;

import com.infinitechnic.horseracing.data.hkjc.entity.BaseEntity;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.util.DateUtil;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HorseDaoImpl implements HorseDao {
    @Autowired
    private Datastore datastore;

    @Override
    public Horse getHorseById(String id) {
//        return datastore.find(Horse.class).field("id").equal(new ObjectId(id)).get();
        return datastore.find(Horse.class, "_id", id).get();
    }

    @Override
    public Horse saveHorse(Horse horse) {
        datastore.save(horse);
        return horse;
    }
}
