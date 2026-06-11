package com.votacion.sistema_votacion.dao.impl;

import com.votacion.sistema_votacion.dao.interfaces.IVotoDAO;
import com.votacion.sistema_votacion.model.Voto;
import com.votacion.sistema_votacion.repository.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementación DAO para la entidad Voto.

 */
@Component
public class VotoDAOImpl implements IVotoDAO {

    private static final Logger logger = LoggerFactory.getLogger(VotoDAOImpl.class);

    @Autowired
    private VotoRepository votoRepository;


    @Override
    public Voto save(Voto voto) {
        logger.info("DAO - Registrando voto para votante ID: {} en elección ID: {}",
                voto.getVotante().getId(), voto.getEleccion().getId());
        return votoRepository.save(voto);
    }

    @Override
    public Optional<Voto> findById(Long id) {
        logger.info("DAO - Buscando voto por ID: {}", id);
        return votoRepository.findById(id);
    }

    @Override
    public List<Voto> findAll() {
        logger.info("DAO - Listando todos los votos");
        return votoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.warn("DAO - Eliminando voto ID: {} (operación restringida)", id);
        votoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return votoRepository.existsById(id);
    }

    @Override
    public long count() {
        return votoRepository.count();
    }


    @Override
    public boolean existsByVotanteIdAndEleccionId(Long idVotante, Long idEleccion) {
        logger.info("DAO - Verificando voto único: votante {} en elección {}", idVotante, idEleccion);
        return votoRepository.existsByVotanteIdAndEleccionId(idVotante, idEleccion);
    }

    @Override
    public List<Voto> findByEleccionId(Long idEleccion) {
        logger.info("DAO - Listando votos de la elección ID: {}", idEleccion);
        return votoRepository.findByEleccionId(idEleccion);
    }

    @Override
    public List<Voto> findByCandidatoIdAndEleccionId(Long idCandidato, Long idEleccion) {
        logger.info("DAO - Listando votos del candidato {} en elección {}", idCandidato, idEleccion);
        return votoRepository.findByCandidatoIdAndEleccionId(idCandidato, idEleccion);
    }

    @Override
    public long countByCandidatoIdAndEleccionId(Long idCandidato, Long idEleccion) {
        long total = votoRepository.countByCandidatoIdAndEleccionId(idCandidato, idEleccion);
        logger.info("DAO - Votos candidato {}: {}", idCandidato, total);
        return total;
    }

    @Override
    public long countByEleccionId(Long idEleccion) {
        long total = votoRepository.countByEleccionId(idEleccion);
        logger.info("DAO - Total de votos en elección {}: {}", idEleccion, total);
        return total;
    }

    @Override
    public Optional<Voto> findByVotanteIdAndEleccionId(Long idVotante, Long idEleccion) {
        return votoRepository.findByVotanteIdAndEleccionId(idVotante, idEleccion);
    }
}
