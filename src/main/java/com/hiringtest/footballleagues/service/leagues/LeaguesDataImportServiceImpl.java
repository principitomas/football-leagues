package com.hiringtest.footballleagues.service.leagues;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiringtest.footballleagues.domain.League;
import com.hiringtest.footballleagues.repository.leagues.LeaguesRepository;
import com.hiringtest.footballleagues.service.footballdata.FootballDataService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class LeaguesDataImportServiceImpl implements LeaguesDataImportService {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();


    private LeaguesRepository leaguesRepository;

    private FootballDataService footballDataService;

    @Autowired
    public LeaguesDataImportServiceImpl(final LeaguesRepository leaguesRepository, final FootballDataService footballDataService) {
        this.leaguesRepository = leaguesRepository;
        this.footballDataService = footballDataService;
    }

    @Override
    public Response importLeagueData(String leagueCode) {

        if (leaguesRepository.findLeagueByCode(leagueCode) != null) {
            return Response
                    .ok()
                    .status(Response.Status.CONFLICT)
                    .entity("{ \"message\" : \"League already imported\" }")
                    .build();
        }

        try {
            League newLeague = footballDataService.getLeagueData(leagueCode);
            if (newLeague == null) {
                return Response
                        .ok()
                        .status(Response.Status.NOT_FOUND)
                        .entity("{ \"message\" : \"Not found\" }")
                        .build();
            }

            leaguesRepository.save(newLeague);
            return Response
                    .ok()
                    .status(Response.Status.CREATED)
                    .entity("{ \"message\" : \"Successfully imported\" }")
                    .build();
        } catch (Exception e) {
            return Response
                    .serverError()
                    .status(Response.Status.GATEWAY_TIMEOUT)
                    .entity("{ \"message\" : \"Server Error\" }")
                    .build();
        }
    }




}