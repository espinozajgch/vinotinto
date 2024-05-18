package com.barrica.vinotinto.infrastructure.adapter.repository;

import com.barrica.vinotinto.infrastructure.adapter.entity.PlayerMatchDbo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerMatchRepository extends JpaRepository<PlayerMatchDbo, Integer> {
}
