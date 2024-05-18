package com.barrica.vinotinto.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "player")
@Data
public class PlayerDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "player_name")
    String playerName;

    @Column(name = "player_id_sofascore")
    Integer playerIdSofaScore;

    @Column(name = "player_position")
    String playerPosition;

    @Column(name = "player_height")
    Integer playerHeight;

    @Column(name = "player_number")
    Integer playerNumber;

    @Column(name = "player_preferredfoot")
    String playerPreferredFoot;

    @Column(name = "player_dateofbirth")
    String playerDateOfBirth;

    @Column(name = "player_gender")
    String playerGender;

    @Column(name = "team")
    String team;

    @Column(name = "team_id_sofascore")
    Integer teamIdSofaScore;

    @Column(name = "team_country")
    String teamCountry;

    @Column(name = "twitter")
    String twitter;

    @Column(name = "ig")
    String ig;

    @Column(name = "team_twitter")
    String teamTwitter;

    @Column(name = "has_match")
    Boolean hasMatch;



}
