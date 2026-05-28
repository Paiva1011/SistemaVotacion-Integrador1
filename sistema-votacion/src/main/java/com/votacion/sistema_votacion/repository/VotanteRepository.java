package com.votacion.sistema_votacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.votacion.sistema_votacion.model.Votante;

public interface VotanteRepository extends JpaRepository<Votante, Long> {

    Optional<Votante> findByDni(String dni);

    Optional<Votante> findByEmail(String email);

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);
}