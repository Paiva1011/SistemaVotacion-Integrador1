package com.votacion.sistema_votacion.dao.impl;

import com.votacion.sistema_votacion.dao.interfaces.IVotanteDAO;
import com.votacion.sistema_votacion.model.Votante;
import com.votacion.sistema_votacion.repository.VotanteRepository;
import com.votacion.sistema_votacion.repository.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementación DAO para la entidad Votante.
 
 */
@Component
public class VotanteDAOImpl implements IVotanteDAO {

    private static final Logger logger = LoggerFactory.getLogger(VotanteDAOImpl.class);

    @Autowired
    private VotanteRepository votanteRepository;

    @Autowired
    private VotoRepository votoRepository;


    @Override
    public Votante save(Votante votante) {
        logger.info("DAO - Guardando votante con DNI: {}", votante.getDni());
        return votanteRepository.save(votante);
    }

    @Override
    public Optional<Votante> findById(Long id) {
        logger.info("DAO - Buscando votante por ID: {}", id);
        return votanteRepository.findById(id);
    }

    @Override
    public List<Votante> findAll() {
        logger.info("DAO - Listando todos los votantes");
        return votanteRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DAO - Eliminando votante ID: {}", id);
        votanteRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return votanteRepository.existsById(id);
    }

    @Override
    public long count() {
        return votanteRepository.count();
    }


    @Override
    public Optional<Votante> findByDni(String dni) {
        logger.info("DAO - Buscando votante por DNI: {}", dni);
        return votanteRepository.findByDni(dni);
    }

    @Override
    public boolean existsByDni(String dni) {
        return votanteRepository.existsByDni(dni);
    }

    @Override
    public boolean yaVotoEnEleccion(Long idVotante, Long idEleccion) {
        boolean yaVoto = votoRepository.existsByVotanteIdAndEleccionId(idVotante, idEleccion);
        logger.info("DAO - Votante {} ya votó en elección {}: {}", idVotante, idEleccion, yaVoto);
        return yaVoto;
    }

    @Override
    public Optional<Votante> findByCelular(String celular) {
        logger.info("DAO - Buscando votante por celular: {}", celular);
        return votanteRepository.findByCelular(celular);
    }
}
