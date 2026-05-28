package com.votacion.sistema_votacion.repository;

import com.votacion.sistema_votacion.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CandidatoRepository extends JpaRepository<Candidato, Long>{
    
}
