package com.votacion.sistema_votacion.service;

import com.votacion.sistema_votacion.dao.interfaces.IComprobanteDAO;
import com.votacion.sistema_votacion.dao.interfaces.IEleccionDAO;
import com.votacion.sistema_votacion.dao.interfaces.IVotanteDAO;
import com.votacion.sistema_votacion.dao.interfaces.IVotoDAO;
import com.votacion.sistema_votacion.model.Candidato;
import com.votacion.sistema_votacion.model.Comprobante;
import com.votacion.sistema_votacion.model.Eleccion;
import com.votacion.sistema_votacion.model.Votante;
import com.votacion.sistema_votacion.model.Voto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Servicio de negocio para la emisión de votos.

 */
@Service
@Transactional
public class VotoService {

    private static final Logger logger = LoggerFactory.getLogger(VotoService.class);

    @Autowired
    private IVotoDAO votoDAO;

    @Autowired
    private IVotanteDAO votanteDAO;

    @Autowired
    private IEleccionDAO eleccionDAO;

    @Autowired
    private IComprobanteDAO comprobanteDAO;

     * @param idVotante   ID del votante autenticado
     * @param idCandidato ID del candidato elegido
     * @param idEleccion  ID de la elección en curso
     * @return Comprobante generado tras el voto exitoso
     * @throws IllegalStateException si ya votó o la elección no está activa
     */
    public Comprobante emitirVoto(Long idVotante, Long idCandidato, Long idEleccion) {

        Eleccion eleccion = eleccionDAO.findById(idEleccion)
                .orElseThrow(() -> new IllegalArgumentException("Elección no encontrada: " + idEleccion));

        if (!eleccionDAO.isEleccionActiva(idEleccion)) {
            logger.warn("SERVICE - Intento de voto en elección no activa: {}", idEleccion);
            throw new IllegalStateException("La elección no está activa.");
        }

        Votante votante = votanteDAO.findById(idVotante)
                .orElseThrow(() -> new IllegalArgumentException("Votante no encontrado: " + idVotante));

        if (votoDAO.existsByVotanteIdAndEleccionId(idVotante, idEleccion)) {
            logger.warn("SERVICE - Votante {} ya emitió su voto en elección {}", idVotante, idEleccion);
            throw new IllegalStateException("El votante ya emitió su voto en esta elección.");
        }

        Candidato candidato = new Candidato();
        candidato.setId(idCandidato);

        Voto voto = new Voto();
        voto.setVotante(votante);
        voto.setCandidato(candidato);
        voto.setEleccion(eleccion);
        voto.setFechaVoto(LocalDateTime.now());

        Voto votoGuardado = votoDAO.save(voto);
        logger.info("SERVICE - Voto registrado exitosamente. ID voto: {}", votoGuardado.getId());

        Comprobante comprobante = new Comprobante();
        comprobante.setVoto(votoGuardado);
        comprobante.setCodigoUnico(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        comprobante.setFechaEmision(LocalDateTime.now());

        Comprobante comprobanteGuardado = comprobanteDAO.save(comprobante);
        logger.info("SERVICE - Comprobante generado: {}", comprobanteGuardado.getCodigoUnico());

        return comprobanteGuardado;
    }


    @Transactional(readOnly = true)
    public Comprobante obtenerComprobante(Long idVotante, Long idEleccion) {
        return comprobanteDAO.findByVotanteIdAndEleccionId(idVotante, idEleccion)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró comprobante para el votante " + idVotante));
    }


    @Transactional(readOnly = true)
    public long totalVotosPorEleccion(Long idEleccion) {
        return votoDAO.countByEleccionId(idEleccion);
    }

    @Transactional(readOnly = true)
    public long votosPorCandidato(Long idCandidato, Long idEleccion) {
        return votoDAO.countByCandidatoIdAndEleccionId(idCandidato, idEleccion);
    }
}
