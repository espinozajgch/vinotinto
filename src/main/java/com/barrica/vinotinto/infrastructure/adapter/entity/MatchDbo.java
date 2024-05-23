package com.barrica.vinotinto.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "match_description")
@Data
public class MatchDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "match_id_sofascore")
    private Integer matchIdSofaScore;

    @Column(name = "hometeam")
    private String homeTeam;

    @Column(name = "homescore")
    private Short homeScore;

    @Column(name = "awayteam")
    private String awayTeam;

    @Column(name = "awayscore")
    private Short awayScore;

    @Column(name = "tournament_id_sofascore")
    private Integer tournamentIdSofaScore;

    @Column(name = "tournament_name")
    private String tournamentName;

    @Column(name = "tournament_stage")
    private String tournamentStage;

    @Column(name = "season_id_sofascore")
    private Integer seasonIdSofaScore;

    @Column(name = "season_name")
    private String seasonName;

    @Column(name = "season_year")
    private String seasonYear;

    @Column(name = "match_date")
    String matchDate;

    @Column(name = "has_global_highlights")
    Boolean hasGlobalHighlights;

    @Column(name = "tournament_country")
    private String tournamentCountry;

    @Column(name = "alfa2")
    private String alfa2;

    @Column(name = "alfa3")
    private String alfa3;

    @Column(name = "hometeam_short_name")
    private String homeTeamShortName;

    @Column(name = "awayteam_short_name")
    private String awayTeamShortName;

    @Column(name = "match_city")
    private String matchCity;

    @Column(name = "match_stadium")
    private String matchStadium;

    @Column(name = "match_country")
    private String matchCountry;

    @Column(name = "match_referee")
    private String matchReferee;

    @Column(name = "match_referee_country")
    private String matchRefereeCountry;

    @Column(name = "hometeam_id_sofascore")
    private Integer hometeamIdSofaScore;

    @Column(name = "awayteam_id_sofascore")
    private Integer awayteamIdSofaScore;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "match_timestamp")
    private String matchTimestamp;

    @Column(name = "match_status")
    private Integer status;

    @Column(name = "home_overtime_score")
    Integer homeOvertimeScore;

    @Column(name = "away_overtime_score")
    Integer awayOvertimeScore;

    @Column(name = "home_penalty_score")
    Integer homePenaltyScore;

    @Column(name = "away_penalty_score")
    Integer awayPenaltyScore;
}
