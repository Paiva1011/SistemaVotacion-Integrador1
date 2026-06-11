package com.votacion.sistema_votacion.dao.impl;

import com.votacion.sistema_votacion.dao.interfaces.IPartidoPoliticoDAO;
import com.votacion.sistema_votacion.model.PartidoPolitico;
import com.votacion.sistema_votacion.repository.PartidoPoliticoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementación DAO para la entidad PartidoPolitico.
 */
@Component
public class PartidoPoliticoDAOImpl implements IPartidoPoliticoDAO {

    private static final Logger logger = LoggerFactory.getLogger(PartidoPoliticoDAOImpl.class);

    @Autowired
    private PartidoPoliticoRepository partidoPoliticoRepository;

    @Override
    public PartidoPolitico save(PartidoPolitico partido) {
        logger.info("DAO - Guardando partido político: {}", partido.getNombre());
        return partidoPoliticoRepository.save(partido);
    }

    @Override
    public Optional<PartidoPolitico> findById(Long id) {
        return partidoPoliticoRepository.findById(id);
    }

    @Override
    public List<PartidoPolitico> findAll() {
        return partidoPoliticoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DAO - Eliminando partido político ID: {}", id);
        partidoPoliticoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return partidoPoliticoRepository.existsById(id);
    }

    @Override
    public long count() {
        return partidoPoliticoRepository.count();
    }


    @Override
    public Optional<PartidoPolitico> findByNombre(String nombre) {
        logger.info("DAO - Buscando partido por nombre: {}", nombre);
        return partidoPoliticoRepository.findByNombre(nombre);
    }

    @Override
    public Optional<PartidoPolitico> findBySimbolo(String simbolo) {
        logger.info("DAO - Buscando partido por símbolo: {}", simbolo);
        return partidoPoliticoRepository.findBySimbolo(simbolo);
    }

    @Override
    public List<PartidoPolitico> findAllOrderByNombre() {
        return partidoPoliticoRepository.findAllByOrderByNombreAsc();
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return partidoPoliticoRepository.existsByNombre(nombre);
    }
}
