package com.votacion.sistema_votacion.dao.interfaces;

import com.votacion.sistema_votacion.model.PartidoPolitico;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para la entidad PartidoPolitico.
 */
public interface IPartidoPoliticoDAO extends IGenericDAO<PartidoPolitico, Long> {

    Optional<PartidoPolitico> findByNombre(String nombre);

    Optional<PartidoPolitico> findBySimbolo(String simbolo);

    List<PartidoPolitico> findAllOrderByNombre();

    boolean existsByNombre(String nombre);
}
