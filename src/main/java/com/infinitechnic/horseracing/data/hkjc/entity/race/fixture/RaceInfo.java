package com.infinitechnic.horseracing.data.hkjc.entity.race.fixture;

public class RaceInfo {
    private String group;
    private Integer distance;
    private String type;    //C=Cup Race P=Priority to Run R=Restricted S=Special Condition
    //TODO: (1)?
    //TODO: All Weather Track?
    //TODO: 80-100?

    public RaceInfo() {
        super();
        this.group = null;
        this.distance = null;
        this.type = null;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
