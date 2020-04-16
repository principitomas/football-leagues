/*
 * This file is generated by jOOQ.
 */
package org.jooq.codegen.footballleagues.tables;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.codegen.footballleagues.FootballLeagues;
import org.jooq.codegen.footballleagues.Keys;
import org.jooq.codegen.footballleagues.tables.records.PlayerRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UShort;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Player extends TableImpl<PlayerRecord> {

    private static final long serialVersionUID = 1716590057;

    /**
     * The reference instance of <code>football_leagues.player</code>
     */
    public static final Player PLAYER = new Player();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlayerRecord> getRecordType() {
        return PlayerRecord.class;
    }

    /**
     * The column <code>football_leagues.player.id</code>.
     */
    public final TableField<PlayerRecord, UShort> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>football_leagues.player.name</code>.
     */
    public final TableField<PlayerRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>football_leagues.player.position</code>.
     */
    public final TableField<PlayerRecord, Integer> POSITION = createField(DSL.name("position"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>football_leagues.player.date_of_birth</code>.
     */
    public final TableField<PlayerRecord, LocalDate> DATE_OF_BIRTH = createField(DSL.name("date_of_birth"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>football_leagues.player.country_of_birth</code>.
     */
    public final TableField<PlayerRecord, String> COUNTRY_OF_BIRTH = createField(DSL.name("country_of_birth"), org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>football_leagues.player.nationality</code>.
     */
    public final TableField<PlayerRecord, String> NATIONALITY = createField(DSL.name("nationality"), org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>football_leagues.player.team_id</code>.
     */
    public final TableField<PlayerRecord, UShort> TEAM_ID = createField(DSL.name("team_id"), org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false), this, "");

    /**
     * Create a <code>football_leagues.player</code> table reference
     */
    public Player() {
        this(DSL.name("player"), null);
    }

    /**
     * Create an aliased <code>football_leagues.player</code> table reference
     */
    public Player(String alias) {
        this(DSL.name(alias), PLAYER);
    }

    /**
     * Create an aliased <code>football_leagues.player</code> table reference
     */
    public Player(Name alias) {
        this(alias, PLAYER);
    }

    private Player(Name alias, Table<PlayerRecord> aliased) {
        this(alias, aliased, null);
    }

    private Player(Name alias, Table<PlayerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Player(Table<O> child, ForeignKey<O, PlayerRecord> key) {
        super(child, key, PLAYER);
    }

    @Override
    public Schema getSchema() {
        return FootballLeagues.FOOTBALL_LEAGUES;
    }

    @Override
    public Identity<PlayerRecord, UShort> getIdentity() {
        return Keys.IDENTITY_PLAYER;
    }

    @Override
    public UniqueKey<PlayerRecord> getPrimaryKey() {
        return Keys.KEY_PLAYER_PRIMARY;
    }

    @Override
    public List<UniqueKey<PlayerRecord>> getKeys() {
        return Arrays.<UniqueKey<PlayerRecord>>asList(Keys.KEY_PLAYER_PRIMARY);
    }

    @Override
    public List<ForeignKey<PlayerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PlayerRecord, ?>>asList(Keys.FK_TEAM_TEAM_ID);
    }

    public Team team() {
        return new Team(this, Keys.FK_TEAM_TEAM_ID);
    }

    @Override
    public Player as(String alias) {
        return new Player(DSL.name(alias), this);
    }

    @Override
    public Player as(Name alias) {
        return new Player(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Player rename(String name) {
        return new Player(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Player rename(Name name) {
        return new Player(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<UShort, String, Integer, LocalDate, String, String, UShort> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
