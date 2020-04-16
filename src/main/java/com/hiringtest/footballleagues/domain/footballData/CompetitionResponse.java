package com.hiringtest.footballleagues.domain.footballData;

import com.google.common.collect.Lists;

import java.util.List;

public class CompetitionResponse {
    private Integer count;
    private Filters filters;
    private Competition competition;
    private Season season;
    private List< CompetitionTeam > teams = Lists.newArrayList();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<CompetitionTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<CompetitionTeam> teams) {
        this.teams = teams;
    }
}


