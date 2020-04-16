/*
 * This file is generated by jOOQ.
 */
package org.jooq.codegen.footballleagues.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.codegen.footballleagues.tables.TeamCompetition;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UShort;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TeamCompetitionRecord extends UpdatableRecordImpl<TeamCompetitionRecord> implements Record3<UShort, UShort, UShort> {

    private static final long serialVersionUID = 588821190;

    /**
     * Setter for <code>football_leagues.team_competition.id</code>.
     */
    public void setId(UShort value) {
        set(0, value);
    }

    /**
     * Getter for <code>football_leagues.team_competition.id</code>.
     */
    public UShort getId() {
        return (UShort) get(0);
    }

    /**
     * Setter for <code>football_leagues.team_competition.team_id</code>.
     */
    public void setTeamId(UShort value) {
        set(1, value);
    }

    /**
     * Getter for <code>football_leagues.team_competition.team_id</code>.
     */
    public UShort getTeamId() {
        return (UShort) get(1);
    }

    /**
     * Setter for <code>football_leagues.team_competition.competition_id</code>.
     */
    public void setCompetitionId(UShort value) {
        set(2, value);
    }

    /**
     * Getter for <code>football_leagues.team_competition.competition_id</code>.
     */
    public UShort getCompetitionId() {
        return (UShort) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UShort> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UShort, UShort, UShort> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UShort, UShort, UShort> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UShort> field1() {
        return TeamCompetition.TEAM_COMPETITION.ID;
    }

    @Override
    public Field<UShort> field2() {
        return TeamCompetition.TEAM_COMPETITION.TEAM_ID;
    }

    @Override
    public Field<UShort> field3() {
        return TeamCompetition.TEAM_COMPETITION.COMPETITION_ID;
    }

    @Override
    public UShort component1() {
        return getId();
    }

    @Override
    public UShort component2() {
        return getTeamId();
    }

    @Override
    public UShort component3() {
        return getCompetitionId();
    }

    @Override
    public UShort value1() {
        return getId();
    }

    @Override
    public UShort value2() {
        return getTeamId();
    }

    @Override
    public UShort value3() {
        return getCompetitionId();
    }

    @Override
    public TeamCompetitionRecord value1(UShort value) {
        setId(value);
        return this;
    }

    @Override
    public TeamCompetitionRecord value2(UShort value) {
        setTeamId(value);
        return this;
    }

    @Override
    public TeamCompetitionRecord value3(UShort value) {
        setCompetitionId(value);
        return this;
    }

    @Override
    public TeamCompetitionRecord values(UShort value1, UShort value2, UShort value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TeamCompetitionRecord
     */
    public TeamCompetitionRecord() {
        super(TeamCompetition.TEAM_COMPETITION);
    }

    /**
     * Create a detached, initialised TeamCompetitionRecord
     */
    public TeamCompetitionRecord(UShort id, UShort teamId, UShort competitionId) {
        super(TeamCompetition.TEAM_COMPETITION);

        set(0, id);
        set(1, teamId);
        set(2, competitionId);
    }
}
