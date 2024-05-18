package com.barrica.vinotinto.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "match_highlights")
@Data
public class MatchHighlightsDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "match_id_sofascore")
    private Integer matchId;

    @Column(name = "url")
    private String url;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

}
