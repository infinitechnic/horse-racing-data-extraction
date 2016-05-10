package com.infinitechnic.horseracing.data.hkjc;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.testng.annotations.Test;

import javax.swing.plaf.synth.SynthTextAreaUI;

public class MongoDbTest {
    @Test
    public void queryMongoDb() {
        MongoClient mongoClient = new MongoClient("10.0.10.214", 27017);
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
