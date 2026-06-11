package com.votacion.sistema_votacion.dao.impl;

import com.votacion.sistema_votacion.dao.interfaces.IEleccionDAO;
import com.votacion.sistema_votacion.model.Eleccion;
import com.votacion.sistema_votacion.repository.EleccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementación DAO para la entidad Eleccion.
 
 */
@Component
public class EleccionDAOImpl implements IEleccionDAO {

    private static final Logger logger = LoggerFactory.getLogger(EleccionDAOImpl.class);

    @Autowired
    private EleccionRepository eleccionRepository;

    @Override
    public Eleccion save(Eleccion eleccion) {
        logger.info("DAO - Guardando elección: {}", eleccion.getNombre());
        return eleccionRepository.save(eleccion);
    }

    @Override
    public Optional<Eleccion> findById(Long id) {
        logger.info("DAO - Buscando elección por ID: {}", id);
        return eleccionRepository.findById(id);
    }

    @Override
    public List<Eleccion> findAll() {
        logger.info("DAO - Listando todas las elecciones");
        return eleccionRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DAO - Eliminando elección ID: {}", id);
        eleccionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return eleccionRepository.existsById(id);
    }

    @Override
    public long count() {
        return eleccionRepository.count();
    }

    // ─── Consultas específicas ───────────────────────────────────────────────

    @Override
    public List<Eleccion> findByEstado(String estado) {
        logger.info("DAO - Buscando elecciones con estado: {}", estado);
        return eleccionRepository.findByEstado(estado);
    }

    @Override
    public List<Eleccion> findByNombreContaining(String nombre) {
        logger.info("DAO - Buscando elecciones por nombre: {}", nombre);
        return eleccionRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Eleccion> findEleccionesActivasOrderByFechaInicio() {
        logger.info("DAO - Buscando elecciones ACTIVAS ordenadas por fecha");
        return eleccionRepository.findByEstadoOrderByFechaInicio("ACTIVA");
    }

    @Override
    public boolean isEleccionActiva(Long idEleccion) {
        return eleccionRepository.findById(idEleccion)
                .map(e -> "ACTIVA".equalsIgnoreCase(e.getEstado()))
                .orElse(false);
    }
}
