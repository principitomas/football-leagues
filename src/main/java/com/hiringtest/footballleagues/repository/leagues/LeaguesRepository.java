package com.hiringtest.footballleagues.repository.leagues;

import com.hiringtest.footballleagues.domain.League;

public interface LeaguesRepository {

    public League findLeagueByCode(String leagueCode);
    public void save(League league);
    public Integer getTotalPlayers(String leagueCode);
}
