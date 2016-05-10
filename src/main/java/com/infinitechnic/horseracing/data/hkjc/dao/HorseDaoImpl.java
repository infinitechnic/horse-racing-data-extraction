package com.infinitechnic.horseracing.data.hkjc.dao;

import com.infinitechnic.horseracing.data.hkjc.entity.Horse;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class HorseDaoImpl implements HorseDao {
    @Autowired
    private Datastore datastore;

    @Override
    public Horse getHorseById(String id) {
        return datastore.find(Horse.class).field("id").equal(new ObjectId(id)).get();
    }
}
