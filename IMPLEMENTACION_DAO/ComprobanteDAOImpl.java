package com.votacion.sistema_votacion.dao.impl;

import com.votacion.sistema_votacion.dao.interfaces.IComprobanteDAO;
import com.votacion.sistema_votacion.model.Comprobante;
import com.votacion.sistema_votacion.repository.ComprobanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementación DAO para la entidad Comprobante.
 */
@Component
public class ComprobanteDAOImpl implements IComprobanteDAO {

    private static final Logger logger = LoggerFactory.getLogger(ComprobanteDAOImpl.class);

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Override
    public Comprobante save(Comprobante comprobante) {
        logger.info("DAO - Guardando comprobante para voto ID: {}",
                comprobante.getVoto() != null ? comprobante.getVoto().getId() : "N/A");
        return comprobanteRepository.save(comprobante);
    }

    @Override
    public Optional<Comprobante> findById(Long id) {
        return comprobanteRepository.findById(id);
    }

    @Override
    public List<Comprobante> findAll() {
        return comprobanteRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.warn("DAO - Eliminando comprobante ID: {}", id);
        comprobanteRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return comprobanteRepository.existsById(id);
    }

    @Override
    public long count() {
        return comprobanteRepository.count();
    }

    @Override
    public Optional<Comprobante> findByVotoId(Long idVoto) {
        logger.info("DAO - Buscando comprobante por voto ID: {}", idVoto);
        return comprobanteRepository.findByVotoId(idVoto);
    }

    @Override
    public Optional<Comprobante> findByVotanteIdAndEleccionId(Long idVotante, Long idEleccion) {
        logger.info("DAO - Buscando comprobante de votante {} en elección {}", idVotante, idEleccion);
        return comprobanteRepository.findByVotoVotanteIdAndVotoEleccionId(idVotante, idEleccion);
    }

    @Override
    public boolean existsByVotanteIdAndEleccionId(Long idVotante, Long idEleccion) {
        return comprobanteRepository.existsByVotoVotanteIdAndVotoEleccionId(idVotante, idEleccion);
    }
}
