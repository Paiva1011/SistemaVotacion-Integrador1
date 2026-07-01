package com.votacion.sistema_votacion.service;

import com.votacion.sistema_votacion.model.*;
import com.votacion.sistema_votacion.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    // Verifica si el votante ya votó en una elección
    public boolean yaVoto(Votante votante, Eleccion eleccion) {
        return votoRepository.findByVotanteAndEleccion(votante, eleccion).isPresent();
    }

    // Guarda el voto
    public Voto guardarVoto(Votante votante, Candidato candidato, Eleccion eleccion) {
        Voto voto = new Voto(votante, candidato, eleccion, LocalDateTime.now());
        return votoRepository.save(voto);
    }
}