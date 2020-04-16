/*
 * This file is generated by jOOQ.
 */
package org.jooq.codegen.footballleagues.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.codegen.footballleagues.tables.Team;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UShort;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TeamRecord extends UpdatableRecordImpl<TeamRecord> implements Record6<UShort, String, String, String, String, String> {

    private static final long serialVersionUID = 1074331328;

    /**
     * Setter for <code>football_leagues.team.id</code>.
     */
    public void setId(UShort value) {
        set(0, value);
    }

    /**
     * Getter for <code>football_leagues.team.id</code>.
     */
    public UShort getId() {
        return (UShort) get(0);
    }

    /**
     * Setter for <code>football_leagues.team.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>football_leagues.team.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>football_leagues.team.tla</code>.
     */
    public void setTla(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>football_leagues.team.tla</code>.
     */
    public String getTla() {
        return (String) get(2);
    }

    /**
     * Setter for <code>football_leagues.team.short_name</code>.
     */
    public void setShortName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>football_leagues.team.short_name</code>.
     */
    public String getShortName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>football_leagues.team.area_name</code>.
     */
    public void setAreaName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>football_leagues.team.area_name</code>.
     */
    public String getAreaName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>football_leagues.team.email</code>.
     */
    public void setEmail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>football_leagues.team.email</code>.
     */
    public String getEmail() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UShort> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<UShort, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<UShort, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<UShort> field1() {
        return Team.TEAM.ID;
    }

    @Override
    public Field<String> field2() {
        return Team.TEAM.NAME;
    }

    @Override
    public Field<String> field3() {
        return Team.TEAM.TLA;
    }

    @Override
    public Field<String> field4() {
        return Team.TEAM.SHORT_NAME;
    }

    @Override
    public Field<String> field5() {
        return Team.TEAM.AREA_NAME;
    }

    @Override
    public Field<String> field6() {
        return Team.TEAM.EMAIL;
    }

    @Override
    public UShort component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getTla();
    }

    @Override
    public String component4() {
        return getShortName();
    }

    @Override
    public String component5() {
        return getAreaName();
    }

    @Override
    public String component6() {
        return getEmail();
    }

    @Override
    public UShort value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getTla();
    }

    @Override
    public String value4() {
        return getShortName();
    }

    @Override
    public String value5() {
        return getAreaName();
    }

    @Override
    public String value6() {
        return getEmail();
    }

    @Override
    public TeamRecord value1(UShort value) {
        setId(value);
        return this;
    }

    @Override
    public TeamRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public TeamRecord value3(String value) {
        setTla(value);
        return this;
    }

    @Override
    public TeamRecord value4(String value) {
        setShortName(value);
        return this;
    }

    @Override
    public TeamRecord value5(String value) {
        setAreaName(value);
        return this;
    }

    @Override
    public TeamRecord value6(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public TeamRecord values(UShort value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TeamRecord
     */
    public TeamRecord() {
        super(Team.TEAM);
    }

    /**
     * Create a detached, initialised TeamRecord
     */
    public TeamRecord(UShort id, String name, String tla, String shortName, String areaName, String email) {
        super(Team.TEAM);

        set(0, id);
        set(1, name);
        set(2, tla);
        set(3, shortName);
        set(4, areaName);
        set(5, email);
    }
}
