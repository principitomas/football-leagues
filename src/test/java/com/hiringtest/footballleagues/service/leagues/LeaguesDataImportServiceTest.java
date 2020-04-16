package com.hiringtest.footballleagues.service.leagues;

import com.google.common.collect.Lists;
import com.hiringtest.footballleagues.domain.League;
import com.hiringtest.footballleagues.repository.leagues.LeaguesRepository;
import com.hiringtest.footballleagues.service.footballdata.FootballDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LeaguesDataImportServiceTest {

    @Mock
    private LeaguesRepository leaguesRepository;

    @Mock
    private FootballDataService footballDataService;

    private LeaguesDataImportService leaguesDataImportService;

    @Before
    public void setUp() {
        leaguesRepository = mock(LeaguesRepository.class);
        footballDataService =  mock(FootballDataService.class);
        leaguesDataImportService = new LeaguesDataImportServiceImpl(leaguesRepository, footballDataService);
    }
    //TODO: MORE TEST CASES
    @Test
    public void testImportLeagueDataReturnsOkResponseWhenSuccessfullyImported() throws Exception {
        String leagueCode = "FakeImportCode";
        League newLeague = new League("code", "areaName", "name", Lists.newArrayList());

        when(footballDataService.getLeagueData(leagueCode)).thenReturn(newLeague);
        doNothing().when(leaguesRepository).save(newLeague);
        doReturn(null).when(leaguesRepository).findLeagueByCode(leagueCode);

        Response response = leaguesDataImportService.importLeagueData("FakeImportCode");

        assertEquals(201, response.getStatus());
        assertEquals("{ \"message\" : \"Successfully imported\" }", response.getEntity().toString());
    }

    @Test
    public void testImportLeagueDataReturnsConflictResponseWhenAlreadyImported() {
        String leagueCode = "FakeImportCode";
        League league = new League("code", "areaName", "name", Lists.newArrayList());

        doReturn(league).when(leaguesRepository).findLeagueByCode(leagueCode);

        Response response = leaguesDataImportService.importLeagueData("FakeImportCode");

        assertEquals(409, response.getStatus());
        assertEquals("{ \"message\" : \"League already imported\" }", response.getEntity().toString());
    }

    @Test
    public void testImportLeagueDataReturnsNotFoundResponseWhenAlreadyImported() throws Exception {
        String leagueCode = "FakeImportCode";
        League newLeague = new League("code", "areaName", "name", Lists.newArrayList());

        doReturn(null).when(leaguesRepository).findLeagueByCode(leagueCode);
        doReturn(null).when(footballDataService).getLeagueData(leagueCode);

        Response response = leaguesDataImportService.importLeagueData("FakeImportCode");

        assertEquals(404, response.getStatus());
        assertEquals("{ \"message\" : \"Not found\" }", response.getEntity().toString());
    }

    @Test
    public void testImportLeagueDataReturnsTimeoutResponseWhenThereIsAnError() throws Exception {
        String leagueCode = "FakeImportCode";
        League newLeague = new League("code", "areaName", "name", Lists.newArrayList());

        doReturn(null).when(leaguesRepository).findLeagueByCode(leagueCode);
        when(footballDataService.getLeagueData(leagueCode)).thenThrow(Exception.class);
        doNothing().when(leaguesRepository).save(newLeague);

        Response response = leaguesDataImportService.importLeagueData("FakeImportCode");

        assertEquals(504, response.getStatus());
        assertEquals("{ \"message\" : \"Server Error\" }", response.getEntity().toString());
    }
}
