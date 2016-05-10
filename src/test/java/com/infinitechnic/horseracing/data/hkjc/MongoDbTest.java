package com.infinitechnic.horseracing.data.hkjc;

import com.infinitechnic.horseracing.data.hkjc.entity.Horse;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.testng.annotations.Test;

public class MongoDbTest {
    @Test
    public void queryMongoDb() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.infinitechnic.horseracing.data.hkjc");
        Datastore datastore = morphia.createDatastore(mongoClient, "horseracing");
        datastore.ensureIndexes();

        Query<Horse> query = datastore.createQuery(Horse.class);
        query.asList().stream().forEach(h -> {
            System.out.println(h.getId() + ", " + h.getName());
        });
    }
}
