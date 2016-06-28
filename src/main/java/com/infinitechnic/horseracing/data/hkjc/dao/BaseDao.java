package com.infinitechnic.horseracing.data.hkjc.dao;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao {
    @Autowired
    protected Datastore datastore;
}
