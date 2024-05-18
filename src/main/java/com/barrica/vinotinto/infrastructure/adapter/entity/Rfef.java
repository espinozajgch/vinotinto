package com.barrica.vinotinto.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tercera_federacion_home")
@Data
public class Rfef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "team_name")
    private String team_name;

    @Column(name = "team_id_sofascore")
    private Integer team_id_sofascore;

    @Column(name = "position")
    private Integer position;

    @Column(name = "matches")
    private Integer matches;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "draws")
    private Integer draws;

    @Column(name = "losses")
    private Integer losses;

    @Column(name = "scoresfor")
    private Integer scoresfor;

    @Column(name = "scoresagainst")
    private Integer scoresagainst;

    @Column(name = "points")
    private Integer points;

    @Column(name = "grupo")
    private String grupo;

}
