package com.barrica.vinotinto.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "match_rrss")
@Data
public class MatchRrssDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "match_id_sofascore")
    private Integer matchIdSofaScore;

    @Column(name = "tweet_id")
    private String tweetId;

    @Column(name = "message")
    private String message;

}
