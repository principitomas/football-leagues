package com.hiringtest.footballleagues.service.leagues;

import javax.ws.rs.core.Response;

public interface LeaguesDataImportService {

    public Response importLeagueData(String leagueCode);
}
