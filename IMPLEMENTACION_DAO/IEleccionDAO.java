package com.votacion.sistema_votacion.dao.interfaces;

import com.votacion.sistema_votacion.model.Eleccion;
import java.util.List;

/**
 * Interfaz DAO para la entidad Eleccion.
 */
public interface IEleccionDAO extends IGenericDAO<Eleccion, Long> {

    List<Eleccion> findByEstado(String estado);

    List<Eleccion> findByNombreContaining(String nombre);

    List<Eleccion> findEleccionesActivasOrderByFechaInicio();

    boolean isEleccionActiva(Long idEleccion);
}
