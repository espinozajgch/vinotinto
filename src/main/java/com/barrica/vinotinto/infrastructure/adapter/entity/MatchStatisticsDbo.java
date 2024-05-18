package com.barrica.vinotinto.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "match_statistics")
@Data
public class MatchStatisticsDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "player_id_sofascore")
    private Integer playerId;

    @Column(name = "match_id_sofascore")
    private Integer matchId;

    @Column(name = "measure")
    String measure;

    @Column(name = "value")
    String value;
}
