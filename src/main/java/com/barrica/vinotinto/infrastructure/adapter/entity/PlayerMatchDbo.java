package com.barrica.vinotinto.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_match")
@Data
@Builder
@NoArgsConstructor // Agregar un constructor sin argumentos
@AllArgsConstructor // Opcionalmente, agregar un constructor que tome todos los argumentos
public class PlayerMatchDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "player_id_sofascore")
    private Integer playerId;

    @Column(name = "match_id_sofascore")
    private Integer matchId;
}
