package com.votacion.sistema_votacion.dao.interfaces;

import com.votacion.sistema_votacion.model.Otp;
import java.util.Optional;

/**
 * Interfaz DAO para la entidad Otp (One-Time Password).
 */
public interface IOtpDAO extends IGenericDAO<Otp, Long> {

    Optional<Otp> findByDniVotante(String dni);

    boolean validarOtp(String dni, String codigoOtp);

    void deleteByDniVotante(String dni);

    boolean isOtpExpirado(Long idOtp);
}
