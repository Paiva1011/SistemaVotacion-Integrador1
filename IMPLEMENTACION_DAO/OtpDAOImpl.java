package com.votacion.sistema_votacion.dao.impl;

import com.votacion.sistema_votacion.dao.interfaces.IOtpDAO;
import com.votacion.sistema_votacion.model.Otp;
import com.votacion.sistema_votacion.repository.OtpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación DAO para la entidad Otp (One-Time Password).

 */
@Component
public class OtpDAOImpl implements IOtpDAO {

    private static final Logger logger = LoggerFactory.getLogger(OtpDAOImpl.class);

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Otp save(Otp otp) {
        logger.info("DAO - Guardando OTP para DNI: {}", otp.getDniVotante());
        return otpRepository.save(otp);
    }

    @Override
    public Optional<Otp> findById(Long id) {
        return otpRepository.findById(id);
    }

    @Override
    public List<Otp> findAll() {
        return otpRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DAO - Eliminando OTP ID: {}", id);
        otpRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return otpRepository.existsById(id);
    }

    @Override
    public long count() {
        return otpRepository.count();
    }


    @Override
    public Optional<Otp> findByDniVotante(String dni) {
        logger.info("DAO - Buscando OTP activo para DNI: {}", dni);
        return otpRepository.findByDniVotante(dni);
    }

    @Override
    public boolean validarOtp(String dni, String codigoOtp) {
        logger.info("DAO - Validando OTP para DNI: {}", dni);
        return otpRepository.findByDniVotanteAndCodigo(dni, codigoOtp)
                .map(otp -> {
                    if (otp.getFechaExpiracion() != null &&
                            otp.getFechaExpiracion().isAfter(LocalDateTime.now())) {
                        return true;
                    }
                    logger.warn("DAO - OTP expirado para DNI: {}", dni);
                    return false;
                })
                .orElse(false);
    }

    @Override
    public void deleteByDniVotante(String dni) {
        logger.info("DAO - Eliminando OTP usado para DNI: {}", dni);
        otpRepository.deleteByDniVotante(dni);
    }

    @Override
    public boolean isOtpExpirado(Long idOtp) {
        return otpRepository.findById(idOtp)
                .map(otp -> otp.getFechaExpiracion() != null &&
                        otp.getFechaExpiracion().isBefore(LocalDateTime.now()))
                .orElse(true);
    }
}
