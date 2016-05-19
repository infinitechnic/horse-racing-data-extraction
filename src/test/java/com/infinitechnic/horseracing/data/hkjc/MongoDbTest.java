package com.infinitechnic.horseracing.data.hkjc;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class MongoDbTest extends BaseTest {
    @Autowired
    private Datastore datastore;

    @Test
    public void queryMongoDb() {
        Query<Horse> query = datastore.createQuery(Horse.class);
        query.asList().stream().forEach(h -> {
            System.out.println(h.getId() + ", " + h.getName());
        });
    }
}
