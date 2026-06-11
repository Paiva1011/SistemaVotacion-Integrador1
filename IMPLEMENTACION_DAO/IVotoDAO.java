package com.votacion.sistema_votacion.dao.interfaces;

import com.votacion.sistema_votacion.model.Voto;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para la entidad Voto.
 */
public interface IVotoDAO extends IGenericDAO<Voto, Long> {

    boolean existsByVotanteIdAndEleccionId(Long idVotante, Long idEleccion);

    List<Voto> findByEleccionId(Long idEleccion);

    List<Voto> findByCandidatoIdAndEleccionId(Long idCandidato, Long idEleccion);

    long countByCandidatoIdAndEleccionId(Long idCandidato, Long idEleccion);

    long countByEleccionId(Long idEleccion);

    Optional<Voto> findByVotanteIdAndEleccionId(Long idVotante, Long idEleccion);
}
