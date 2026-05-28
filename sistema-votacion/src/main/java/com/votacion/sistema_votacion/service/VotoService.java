package com.votacion.sistema_votacion.service;

import com.votacion.sistema_votacion.model.*;
import com.votacion.sistema_votacion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private VotanteRepository votanteRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private EleccionRepository eleccionRepository;

    public String emitirVoto(Long votanteId, Long candidatoId) {
        Votante votante = votanteRepository.findById(votanteId)
                .orElseThrow(() -> new RuntimeException("Votante no encontrado"));

        Eleccion eleccion = eleccionRepository.findByActivaTrue()
                .orElseThrow(() -> new RuntimeException("No hay elección activa"));

        if (votoRepository.existsByVotanteAndEleccion(votante, eleccion)) {
            return "Este votante ya emitió su voto";
        }

        Candidato candidato = candidatoRepository.findById(candidatoId)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));

        Voto voto = new Voto();
        voto.setVotante(votante);
        voto.setCandidato(candidato);
        voto.setEleccion(eleccion);
        voto.setFechaVoto(LocalDateTime.now());

        votoRepository.save(voto);

        votante.setYaVoto(true);
        votanteRepository.save(votante);

        return "Voto emitido exitosamente";
    }
}