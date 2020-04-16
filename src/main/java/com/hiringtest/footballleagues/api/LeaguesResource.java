package com.hiringtest.footballleagues.api;

import com.hiringtest.footballleagues.service.leagues.LeaguesDataImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//TODO: add swagger
@Component
@Path("/import-league")
public class LeaguesResource {

    @Autowired
    private LeaguesDataImportServiceImpl leaguesDataImportService;

    @GET
    @Path("/{leagueCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response importLeague(@PathParam("leagueCode") String leagueCode) {
        return leaguesDataImportService.importLeagueData(leagueCode);
    }
}
