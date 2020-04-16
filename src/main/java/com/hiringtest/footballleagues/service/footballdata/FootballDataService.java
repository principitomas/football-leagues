package com.hiringtest.footballleagues.service.footballdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.hiringtest.footballleagues.domain.League;
import com.hiringtest.footballleagues.domain.Player;
import com.hiringtest.footballleagues.domain.Team;
import com.hiringtest.footballleagues.domain.footballData.CompetitionResponse;
import com.hiringtest.footballleagues.domain.footballData.TeamMember;
import com.hiringtest.footballleagues.domain.footballData.TeamResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * I FOUND THIS JAVA API: https://github.com/Airmime/java-football-data
 * BUT IT DOES NOT SEEM TO BE MAINTAINED AND DOES NOT HANDLE LEAGUE CODES
 */

@Service
public class FootballDataService {

    Logger logger = LogManager.getLogger(FootballDataService.class);
    private CloseableHttpClient httpClient;
    private ObjectMapper objectMapper;

    private Environment environment;

    @Autowired
    public FootballDataService(final Environment environment, final ObjectMapper objectMapper, final CloseableHttpClient httpClient) {
        this.environment = environment;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public League getLeagueData(String leagueCode) throws Exception {

        CompetitionResponse competition = requestCompetition(leagueCode);
        if (competition == null) {
            return null;
        }
        List<Team> teams = Lists.newArrayList();
        competition.getTeams()
                .forEach(team -> {
                    TeamResponse teamResponse = null;
                    try {
                        teamResponse = requestTeam(team.getId());
                    } catch (IOException | InterruptedException e) {
                        logger.error("Error while requesting teams.", team,e);
                        throw new RuntimeException(e);
                    }
                    if (teamResponse != null) {
                        teams.add(fromResponseToTeam(teamResponse));
                    }
                });

        return new League(competition.getCompetition().getCode(),
                competition.getCompetition().getArea().getName(),
                competition.getCompetition().getName(),
                teams
        );
    }

    private Team fromResponseToTeam(TeamResponse teamResponse) {
        List<Player> players = teamResponse.getSquad()
                .stream()
                .filter(teamMember -> "PLAYER".equals(teamMember.getRole()))
                .map(this::fromResponseToPlayer)
                .collect(Collectors.toList()
                );

        return new Team(
                teamResponse.getName(),
                teamResponse.getTla(),
                teamResponse.getShortName(),
                teamResponse.getEmail(),
                teamResponse.getArea().getName(),
                players
        );
    }

    private Player fromResponseToPlayer(TeamMember teamMember) {
        Date dateOfBirth = null;
        try {
            //Some players come with null dateOfBirth from football-data.org
            if (teamMember.getDateOfBirth() != null) {
                //Dates come from football-data.org as string
                dateOfBirth = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .parse(
                                teamMember.getDateOfBirth()
                                        .replaceAll("Z$", "+0000")
                        );
            }
        } catch (ParseException e) {
            logger.error("Error while parsing date of birth for team member", teamMember,e);
        }
        return new Player(
                teamMember.getName(),
                teamMember.getPosition(),
                dateOfBirth,
                teamMember.getCountryOfBirth(),
                teamMember.getNationality()
        );
    }

    private CompetitionResponse requestCompetition(String leagueCode) throws IOException, InterruptedException {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Auth-Token", environment.getProperty("football.data.api.auth.token"));
        try {
            CloseableHttpResponse httpResponse = doRequest(environment.getProperty("football.data.api.baseurl") + "/competitions/" + leagueCode + "/teams", headers);
            HttpEntity responseEntity = httpResponse.getEntity();

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return null;
            }

            //TO AVOID GETTING 429 ERRORS: football-data.org free plan is rate limited up to 10 requests per minute
            verifyRateLimit(httpResponse);

            return objectMapper.readValue(EntityUtils.toString(responseEntity), CompetitionResponse.class);
        } catch (IOException | InterruptedException e) {
            logger.error("Error requesting data from" + environment.getProperty("football.data.api.baseurl"), e);
            throw e;
        }
    }

    private TeamResponse requestTeam(Long teamId) throws IOException, InterruptedException {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Auth-Token", environment.getProperty("football.data.api.auth.token"));
        try {
            CloseableHttpResponse httpResponse = doRequest(environment.getProperty("football.data.api.baseurl") + "/teams/" + teamId, headers);
            HttpEntity responseEntity = httpResponse.getEntity();

            //TO AVOID GETTING 429 ERRORS: football-data.org free plan is rate limited up to 10 requests per minute
            verifyRateLimit(httpResponse);

            return objectMapper.readValue(EntityUtils.toString(responseEntity), TeamResponse.class);
        } catch (IOException | InterruptedException e) {
            logger.info("Error requesting data from" + environment.getProperty("football.data.api.baseurl"), e);
            throw e;
        }
    }

    private CloseableHttpResponse doRequest(String url, Map<String, String> headers) throws IOException {
        HttpGet request = new HttpGet(url);

        // add request headers
        headers.keySet().forEach(header -> request.addHeader(header, headers.get(header)));

        return httpClient.execute(request);
    }

    private void verifyRateLimit(CloseableHttpResponse httpResponse) throws InterruptedException {
        if ("0".equals(httpResponse.getFirstHeader("X-Requests-Available-Minute").getValue())) {
            logger.info("Waiting: football-data.org rate limit exceeded.");
            Thread.sleep(Integer.parseInt(httpResponse.getFirstHeader("X-RequestCounter-Reset").getValue()) * 1100);
        }
    }
}
