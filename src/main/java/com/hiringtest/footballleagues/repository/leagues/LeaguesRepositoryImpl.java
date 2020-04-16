package com.hiringtest.footballleagues.repository.leagues;

import com.hiringtest.footballleagues.domain.League;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep6;
import org.jooq.Record;
import org.jooq.types.UShort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.jooq.codegen.footballleagues.tables.Competition.COMPETITION;
import static org.jooq.codegen.footballleagues.tables.Player.PLAYER;
import static org.jooq.codegen.footballleagues.tables.Team.TEAM;
import static org.jooq.codegen.footballleagues.tables.TeamCompetition.TEAM_COMPETITION;


@Service
public class LeaguesRepositoryImpl implements LeaguesRepository {

    @Autowired
    private DSLContext dslContext;

    @Override
    public League findLeagueByCode(String leagueCode) {
        Record record = dslContext.select()
                .from(COMPETITION)
                .where(COMPETITION.CODE.eq(leagueCode))
                .fetchOne();
        return record != null ? record.map(league -> new League(
                league.get(COMPETITION.CODE),
                league.get(COMPETITION.NAME),
                league.get(COMPETITION.AREA_NAME),
                null)) : null;
    }

    @Override
    public void save(League league) {
        int competitionId = dslContext.insertInto(COMPETITION, COMPETITION.NAME, COMPETITION.CODE, COMPETITION.AREA_NAME)
                .values(league.getName(), league.getCode(), league.getAreaName())
                .onDuplicateKeyIgnore()
                .returning(COMPETITION.ID)
                .fetchOne()
                .map(record -> record.getValue(COMPETITION.ID).intValue());

        league.getTeams().forEach(team -> {
            Record teamRecord = dslContext.select()
                    .from(TEAM)
                    .where(TEAM.TLA.eq(team.getTla()).and(TEAM.NAME.eq(team.getName())))
                    .fetchOne();

            //If team does not exist
            if (teamRecord == null) {
                //INSERT TEAM AND RETURN
                int teamId = dslContext.insertInto(TEAM, TEAM.NAME, TEAM.TLA, TEAM.SHORT_NAME, TEAM.EMAIL, TEAM.AREA_NAME)
                        .values(team.getName(), team.getTla(), team.getShortName(), team.getEmail(), team.getAreaName())
                        .onDuplicateKeyIgnore()
                        .returning(TEAM.ID)
                        .fetchOne()
                        .map(record -> record.getValue(TEAM.ID).intValue());

                //INSERT TEAM PlAYERS
                InsertValuesStep6 playerInserts = dslContext.insertInto(PLAYER, PLAYER.NAME, PLAYER.TEAM_ID, PLAYER.POSITION, PLAYER.NATIONALITY, PLAYER.DATE_OF_BIRTH, PLAYER.COUNTRY_OF_BIRTH);
                team.getPlayers().forEach(player -> {
                    playerInserts.values(player.getName(), UShort.valueOf(teamId), player.getPosition(), player.getNationality(), player.getDateOfBirth(), player.getCountryOfBirth()).onDuplicateKeyIgnore();
                });
                playerInserts.execute();

                //INSERT RELATION BETWEEN TEAM AND COMPETITION
                dslContext.insertInto(TEAM_COMPETITION, TEAM_COMPETITION.TEAM_ID, TEAM_COMPETITION.COMPETITION_ID)
                        .values(UShort.valueOf(teamId), UShort.valueOf(competitionId)).execute();
            } else {
                //JUST INSERT RELATION BETWEEN TEAM AND COMPETITION
                dslContext.insertInto(TEAM_COMPETITION, TEAM_COMPETITION.TEAM_ID, TEAM_COMPETITION.COMPETITION_ID)
                        .values(UShort.valueOf(teamRecord.get(TEAM.ID).intValue()), UShort.valueOf(competitionId)).execute();
            }
        });
    }

    @Override
    public Integer getTotalPlayers(String leagueCode) {
        return dslContext.selectCount()
                .from(COMPETITION)
                .leftJoin(TEAM_COMPETITION)
                .on(COMPETITION.ID.eq(TEAM_COMPETITION.COMPETITION_ID))
                .leftJoin(PLAYER)
                .on(PLAYER.TEAM_ID.eq(TEAM_COMPETITION.TEAM_ID))
                .where(COMPETITION.CODE.eq(leagueCode))
                .fetchOne(0, int.class);
    }
}
