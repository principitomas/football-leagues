package com.hiringtest.footballleagues.domain.footballData;

import java.util.ArrayList;

public class TeamResponse {
    private Long id;
    private Area area;
    ArrayList < Object > activeCompetitions = new ArrayList < Object > ();
    private String name;
    private String shortName;
    private String tla;
    private String address;
    private String crestUrl;
    private String phone;
    private String website;
    private String email;
    private float founded;
    private String clubColors;
    private String venue = null;
    private ArrayList< TeamMember > squad = new ArrayList < TeamMember> ();
    private String lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTla() {
        return tla;
    }

    public void setTla(String tla) {
        this.tla = tla;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getFounded() {
        return founded;
    }

    public void setFounded(float founded) {
        this.founded = founded;
    }

    public String getClubColors() {
        return clubColors;
    }

    public void setClubColors(String clubColors) {
        this.clubColors = clubColors;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public ArrayList<TeamMember> getSquad() {
        return squad;
    }

    public void setSquad(ArrayList<TeamMember> squad) {
        this.squad = squad;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<Object> getActiveCompetitions() {
        return activeCompetitions;
    }

    public void setActiveCompetitions(ArrayList<Object> activeCompetitions) {
        this.activeCompetitions = activeCompetitions;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }
}
