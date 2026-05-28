package com.votacion.sistema_votacion.repository;

import com.votacion.sistema_votacion.model.Voto;
import com.votacion.sistema_votacion.model.Votante;
import com.votacion.sistema_votacion.model.Eleccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByVotanteAndEleccion(Votante votante, Eleccion eleccion);
}