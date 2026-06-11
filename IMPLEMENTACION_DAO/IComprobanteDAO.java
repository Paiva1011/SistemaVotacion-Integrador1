package com.votacion.sistema_votacion.dao.interfaces;

import com.votacion.sistema_votacion.model.Comprobante;
import java.util.Optional;

/**
 * Interfaz DAO para la entidad Comprobante.
 */
public interface IComprobanteDAO extends IGenericDAO<Comprobante, Long> {

    Optional<Comprobante> findByVotoId(Long idVoto);

    Optional<Comprobante> findByVotanteIdAndEleccionId(Long idVotante, Long idEleccion);

    boolean existsByVotanteIdAndEleccionId(Long idVotante, Long idEleccion);
}
