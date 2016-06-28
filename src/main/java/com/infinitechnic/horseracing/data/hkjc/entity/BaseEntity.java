package com.infinitechnic.horseracing.data.hkjc.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mongodb.morphia.annotations.Id;

public abstract class BaseEntity<T extends BaseEntity<?>> {
    public static final String FORMAT_DATE = "yyyyMMdd";

    private static final ObjectMapper JSON_OBJECT_MAPPER = new ObjectMapper();
/*
    static {
        JSON_OBJECT_MAPPER.setVisibility(
                JSON_OBJECT_MAPPER.getSerializationConfig().
                        getDefaultVisibilityChecker().
                        withFieldVisibility(JsonAutoDetect.Visibility.ANY).
                        withGetterVisibility(JsonAutoDetect.Visibility.NONE).
                        withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
        );
    }
*/

    @Id
    protected String id;

    protected BaseEntity() {
        super();
        id = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        //TODO: throw exception if old value is not null and new value is diff from old value
        this.id = id;
    }

    @Override
    public String toString() {
        try {
            return JSON_OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }
}
