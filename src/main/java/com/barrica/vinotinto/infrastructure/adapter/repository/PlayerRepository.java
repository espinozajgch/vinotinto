package com.barrica.vinotinto.infrastructure.adapter.repository;

import com.barrica.vinotinto.infrastructure.adapter.entity.PlayerDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerDbo, Integer> {
    List<PlayerDbo> findAllByOrderById();

    List<PlayerDbo> findAllByHasMatchTrueOrderById();

    List<PlayerDbo> findAllByHasMatchFalseOrderById();

}