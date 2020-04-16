package com.hiringtest.footballleagues.api;

import com.hiringtest.footballleagues.dto.TotalPlayers;
import com.hiringtest.footballleagues.repository.leagues.LeaguesRepository;
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
@Path("/total-players")
public class PlayersResource {

    @Autowired
    private LeaguesRepository leaguesRepository;

    @GET
    @Path("{leagueCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalPlayers(@PathParam("leagueCode") String leagueCode) {
        Integer totalPlayers = leaguesRepository.getTotalPlayers(leagueCode);
        if (totalPlayers.intValue() <= 0) {
            return  Response
                    .ok()
                    .status(Response.Status.NOT_FOUND)
                    .entity("{ \"message\" : \"Not found\"}")
                    .build();
        }
        return  Response
                .ok()
                .status(Response.Status.OK)
                .entity(new TotalPlayers(totalPlayers))
                .build();
    }
}
