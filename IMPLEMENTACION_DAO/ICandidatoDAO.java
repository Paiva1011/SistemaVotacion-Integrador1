package com.votacion.sistema_votacion.dao.interfaces;

import com.votacion.sistema_votacion.model.Candidato;
import java.util.List;

/**
 * Interfaz DAO para la entidad Candidato.
 */
public interface ICandidatoDAO extends IGenericDAO<Candidato, Long> {

    List<Candidato> findByEleccionId(Long idEleccion);

    List<Candidato> findByPartidoPoliticoId(Long idPartido);

    List<Candidato> findByApellidos(String apellidos);

    boolean existsByIdAndEleccion(Long idCandidato, Long idEleccion);

    long countVotosByIdCandidatoAndEleccion(Long idCandidato, Long idEleccion);
}
