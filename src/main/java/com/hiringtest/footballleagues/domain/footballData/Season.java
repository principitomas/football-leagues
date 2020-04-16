package com.hiringtest.footballleagues.domain.footballData;

public class Season {
    private Long id;
    private String startDate;
    private String endDate;
    private float currentMatchday;
    private Winner winner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getCurrentMatchday() {
        return currentMatchday;
    }

    public void setCurrentMatchday(float currentMatchday) {
        this.currentMatchday = currentMatchday;
    }

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }
}
