package com.votacion.sistema_votacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacion.sistema_votacion.model.Votante;
import com.votacion.sistema_votacion.repository.VotanteRepository;

@Service
public class VotanteService {

    @Autowired
    private VotanteRepository votanteRepository;

    public String registrarVotante(Votante votante) {

        if (votanteRepository.existsByDni(votante.getDni())) {
            return "El DNI ya existe";
        }

        if (votanteRepository.existsByEmail(votante.getEmail())) {
            return "El email ya existe";
        }

        votanteRepository.save(votante);

        return "Votante registrado correctamente";
    }
}