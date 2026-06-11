package com.votacion.sistema_votacion.dao.interfaces;

import com.votacion.sistema_votacion.model.Votante;
import java.util.Optional;

/**
 * Interfaz DAO para la entidad Votante.
 */
public interface IVotanteDAO extends IGenericDAO<Votante, Long> {

    Optional<Votante> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean yaVotoEnEleccion(Long idVotante, Long idEleccion);

    Optional<Votante> findByCelular(String celular);
}
