package com.votacion.sistema_votacion.dao.impl;

import com.votacion.sistema_votacion.dao.interfaces.ICandidatoDAO;
import com.votacion.sistema_votacion.model.Candidato;
import com.votacion.sistema_votacion.repository.CandidatoRepository;
import com.votacion.sistema_votacion.repository.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del patrón DAO para la entidad Candidato.

 */
@Component
public class CandidatoDAOImpl implements ICandidatoDAO {

    private static final Logger logger = LoggerFactory.getLogger(CandidatoDAOImpl.class);

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Override
    public Candidato save(Candidato candidato) {
        logger.info("DAO - Guardando candidato: {} {}", candidato.getNombres(), candidato.getApellidos());
        return candidatoRepository.save(candidato);
    }

    @Override
    public Optional<Candidato> findById(Long id) {
        logger.info("DAO - Buscando candidato por ID: {}", id);
        return candidatoRepository.findById(id);
    }

    @Override
    public List<Candidato> findAll() {
        logger.info("DAO - Listando todos los candidatos");
        return candidatoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DAO - Eliminando candidato ID: {}", id);
        candidatoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return candidatoRepository.existsById(id);
    }

    @Override
    public long count() {
        return candidatoRepository.count();
    }

    // ─── Consultas específicas ───────────────────────────────────────────────

    @Override
    public List<Candidato> findByEleccionId(Long idEleccion) {
        logger.info("DAO - Buscando candidatos de la elección ID: {}", idEleccion);
        return candidatoRepository.findByEleccionId(idEleccion);
    }

    @Override
    public List<Candidato> findByPartidoPoliticoId(Long idPartido) {
        logger.info("DAO - Buscando candidatos del partido ID: {}", idPartido);
        return candidatoRepository.findByPartidoPoliticoId(idPartido);
    }

    @Override
    public List<Candidato> findByApellidos(String apellidos) {
        logger.info("DAO - Buscando candidatos por apellido: {}", apellidos);
        return candidatoRepository.findByApellidosContainingIgnoreCase(apellidos);
    }

    @Override
    public boolean existsByIdAndEleccion(Long idCandidato, Long idEleccion) {
        return candidatoRepository.existsByIdAndEleccionId(idCandidato, idEleccion);
    }

    @Override
    public long countVotosByIdCandidatoAndEleccion(Long idCandidato, Long idEleccion) {
        logger.info("DAO - Contando votos del candidato {} en elección {}", idCandidato, idEleccion);
        return votoRepository.countByCandidatoIdAndEleccionId(idCandidato, idEleccion);
    }
}
