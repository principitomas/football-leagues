package com.hiringtest.footballleagues.service.footballdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.hiringtest.footballleagues.domain.League;
import com.hiringtest.footballleagues.domain.footballData.*;
import com.hiringtest.footballleagues.service.footballdata.FootballDataService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FootballDataServiceTest {

    private ObjectMapper mockObjectMapper;
    private CloseableHttpClient mockHttpClient;
    private Environment mockEnvironment;
    private FootballDataService footballDataService;

    @Before
    public void setUp() {
        mockObjectMapper = mock(ObjectMapper.class);
        mockHttpClient = mock(CloseableHttpClient.class);
        mockEnvironment = mock(Environment.class);
        footballDataService = new FootballDataService(mockEnvironment, mockObjectMapper, mockHttpClient);
    }
    //TODO: MORE TEST CASES
    @Test
    public void testGetLeagueDataReturnsNullWhenLeagueCodeDoesNotExists() throws Exception {
        String leagueCode = "fakeLeagueCode";
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        StatusLine mockStatusLine = mock(StatusLine.class);

        doReturn(HttpStatus.SC_NOT_FOUND).when(mockStatusLine).getStatusCode();
        doReturn(mockStatusLine).when(mockHttpResponse).getStatusLine();
        doReturn(mockHttpResponse).when(mockHttpClient).execute(any());

        League league = footballDataService.getLeagueData(leagueCode);

        assertNull(league);

        verify(mockHttpClient).execute(any());
        verify(mockHttpResponse).getStatusLine();
        verify(mockStatusLine).getStatusCode();
    }

    @Test
    public void testGetLeagueDataRequestsAllDataAndReturnsALeague() throws Exception {
        String leagueCode = "fakeLeagueCode";

        CloseableHttpResponse mockHttpCompetitionResponse = mock(CloseableHttpResponse.class);
        StatusLine mockCompetitionStatusLine = mock(StatusLine.class);

        CloseableHttpResponse mockHttpTeamResponse = mock(CloseableHttpResponse.class);
        StatusLine mockTeamStatusLine = mock(StatusLine.class);

        CloseableHttpResponse mockHttpTeamResponse2 = mock(CloseableHttpResponse.class);
        StatusLine mockTeamStatusLine2 = mock(StatusLine.class);

        Header mockCounterHeader = mock(Header.class);
        Header mockAvailableHeader = mock(Header.class);
        HttpEntity mockEntity = mock(HttpEntity.class);

        CompetitionResponse competitionResponse = new CompetitionResponse();
        Competition competitionFromServer = new Competition();
        competitionFromServer.setArea(new Area());
        competitionFromServer.setName("competitionName");
        competitionFromServer.setCode("com");
        competitionResponse.setCompetition(competitionFromServer);
        CompetitionTeam competitionTeam1 = new CompetitionTeam();
        competitionTeam1.setId(1L);
        CompetitionTeam competitionTeam2 = new CompetitionTeam();
        competitionTeam2.setId(2L);
        competitionResponse.setTeams(Lists.newArrayList(competitionTeam1, competitionTeam2));

        TeamResponse teamResponse1 = new TeamResponse();
        teamResponse1.setName("team1");
        teamResponse1.setTla("te1");
        teamResponse1.setShortName("tea1");
        teamResponse1.setEmail("team1@team.com");
        teamResponse1.setArea(new Area());

        TeamResponse teamResponse2 = new TeamResponse();
        teamResponse2.setName("team2");
        teamResponse2.setTla("te2");
        teamResponse2.setShortName("tea2");
        teamResponse2.setEmail("team2@team.com");
        teamResponse2.setArea(new Area());

        when(mockHttpClient.execute(any())).thenReturn(mockHttpCompetitionResponse, mockHttpTeamResponse, mockHttpTeamResponse2);

        doReturn("10").when(mockAvailableHeader).getValue();
        doReturn("35").when(mockCounterHeader).getValue();

        doReturn(mockCompetitionStatusLine).when(mockHttpCompetitionResponse).getStatusLine();
        doReturn(mockAvailableHeader).when(mockHttpCompetitionResponse).getFirstHeader(eq("X-Requests-Available-Minute"));
        doReturn(mockCounterHeader).when(mockHttpCompetitionResponse).getFirstHeader(eq("X-RequestCounter-Reset"));
        doReturn(mockEntity).when(mockHttpCompetitionResponse).getEntity();
        doReturn(HttpStatus.SC_ACCEPTED).when(mockCompetitionStatusLine).getStatusCode();
        when(mockObjectMapper.readValue(eq(EntityUtils.toString(mockEntity)), eq(CompetitionResponse.class))).thenReturn(competitionResponse);

        doReturn(mockTeamStatusLine).when(mockHttpTeamResponse).getStatusLine();
        doReturn(mockAvailableHeader).when(mockHttpTeamResponse).getFirstHeader(eq("X-Requests-Available-Minute"));
        doReturn(mockCounterHeader).when(mockHttpTeamResponse).getFirstHeader(eq("X-RequestCounter-Reset"));
        doReturn(mockEntity).when(mockHttpTeamResponse).getEntity();
        doReturn(HttpStatus.SC_ACCEPTED).when(mockTeamStatusLine).getStatusCode();
        when(mockObjectMapper.readValue(eq(EntityUtils.toString(mockEntity)), eq(TeamResponse.class))).thenReturn(teamResponse1);

        doReturn(mockTeamStatusLine2).when(mockHttpTeamResponse2).getStatusLine();
        doReturn(mockAvailableHeader).when(mockHttpTeamResponse2).getFirstHeader(eq("X-Requests-Available-Minute"));
        doReturn(mockCounterHeader).when(mockHttpTeamResponse2).getFirstHeader(eq("X-RequestCounter-Reset"));
        doReturn(mockEntity).when(mockHttpTeamResponse2).getEntity();
        doReturn(HttpStatus.SC_ACCEPTED).when(mockTeamStatusLine2).getStatusCode();
        when(mockObjectMapper.readValue(eq(EntityUtils.toString(mockEntity)), eq(TeamResponse.class))).thenReturn(teamResponse2);

        League league = footballDataService.getLeagueData(leagueCode);

        assertEquals(competitionFromServer.getName(), league.getName());
        assertEquals(competitionFromServer.getCode(), league.getCode());
        assertEquals(competitionFromServer.getArea().getName(), league.getAreaName());

        verify(mockHttpClient, times(3)).execute(any());
        verify(mockHttpCompetitionResponse).getStatusLine();
        verify(mockHttpCompetitionResponse).getEntity();
        verify(mockHttpCompetitionResponse).getFirstHeader(eq("X-Requests-Available-Minute"));
        verify(mockCompetitionStatusLine).getStatusCode();

        verify(mockHttpTeamResponse).getEntity();
        verify(mockHttpTeamResponse).getFirstHeader(eq("X-Requests-Available-Minute"));

        verify(mockHttpTeamResponse2).getEntity();
        verify(mockHttpTeamResponse2).getFirstHeader(eq("X-Requests-Available-Minute"));

        verify(mockObjectMapper).readValue(eq(EntityUtils.toString(mockEntity)), eq(CompetitionResponse.class));
        verify(mockObjectMapper, times(2)).readValue(eq(EntityUtils.toString(mockEntity)), eq(TeamResponse.class));
    }

    @Test (expected = RuntimeException.class)
    public void testGetLeagueDataRequestsThrowsExceptionWhenRequestTeamFails() throws Exception {
        String leagueCode = "fakeLeagueCode";

        CloseableHttpResponse mockHttpCompetitionResponse = mock(CloseableHttpResponse.class);
        StatusLine mockCompetitionStatusLine = mock(StatusLine.class);

        CloseableHttpResponse mockHttpTeamResponse = mock(CloseableHttpResponse.class);
        StatusLine mockTeamStatusLine = mock(StatusLine.class);

        Header mockCounterHeader = mock(Header.class);
        Header mockAvailableHeader = mock(Header.class);
        HttpEntity mockEntity = mock(HttpEntity.class);

        CompetitionResponse competitionResponse = new CompetitionResponse();
        Competition competitionFromServer = new Competition();
        competitionFromServer.setArea(new Area());
        competitionFromServer.setName("competitionName");
        competitionFromServer.setCode("com");
        competitionResponse.setCompetition(competitionFromServer);
        CompetitionTeam competitionTeam1 = new CompetitionTeam();
        competitionTeam1.setId(1L);
        competitionResponse.setTeams(Lists.newArrayList(competitionTeam1));

        TeamResponse teamResponse1 = new TeamResponse();
        teamResponse1.setName("team1");
        teamResponse1.setTla("te1");
        teamResponse1.setShortName("tea1");
        teamResponse1.setEmail("team1@team.com");
        teamResponse1.setArea(new Area());

        when(mockHttpClient.execute(any())).thenReturn(mockHttpCompetitionResponse, mockHttpTeamResponse);

        doReturn("10").when(mockAvailableHeader).getValue();
        doReturn("35").when(mockCounterHeader).getValue();

        doReturn(mockCompetitionStatusLine).when(mockHttpCompetitionResponse).getStatusLine();
        doReturn(mockAvailableHeader).when(mockHttpCompetitionResponse).getFirstHeader(eq("X-Requests-Available-Minute"));
        doReturn(mockCounterHeader).when(mockHttpCompetitionResponse).getFirstHeader(eq("X-RequestCounter-Reset"));
        doReturn(mockEntity).when(mockHttpCompetitionResponse).getEntity();
        doReturn(HttpStatus.SC_ACCEPTED).when(mockCompetitionStatusLine).getStatusCode();
        when(mockObjectMapper.readValue(eq(EntityUtils.toString(mockEntity)), eq(CompetitionResponse.class))).thenReturn(competitionResponse);

        doReturn(mockTeamStatusLine).when(mockHttpTeamResponse).getStatusLine();
        doReturn(mockAvailableHeader).when(mockHttpTeamResponse).getFirstHeader(eq("X-Requests-Available-Minute"));
        doReturn(mockCounterHeader).when(mockHttpTeamResponse).getFirstHeader(eq("X-RequestCounter-Reset"));
        doReturn(mockEntity).when(mockHttpTeamResponse).getEntity();
        doReturn(HttpStatus.SC_ACCEPTED).when(mockTeamStatusLine).getStatusCode();
        when(mockObjectMapper.readValue(eq(EntityUtils.toString(mockEntity)), eq(TeamResponse.class))).thenThrow(IOException.class);

        footballDataService.getLeagueData(leagueCode);
    }
}
