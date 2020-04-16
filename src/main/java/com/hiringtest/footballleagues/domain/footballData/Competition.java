package com.hiringtest.footballleagues.domain.footballData;

public class Competition {
    private float id;
    Area AreaObject;
    private String name;
    private String code;
    private String plan;
    private String lastUpdated;

    public float getId() {
        return id;
    }

    public Area getArea() {
        return AreaObject;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getPlan() {
        return plan;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setArea(Area areaObject) {
        this.AreaObject = areaObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
