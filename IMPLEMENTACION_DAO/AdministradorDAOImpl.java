package com.votacion.sistema_votacion.dao.impl;

import com.votacion.sistema_votacion.dao.interfaces.IAdministradorDAO;
import com.votacion.sistema_votacion.model.Administrador;
import com.votacion.sistema_votacion.repository.AdministradorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementación DAO para la entidad Administrador.
 */
@Component
public class AdministradorDAOImpl implements IAdministradorDAO {

    private static final Logger logger = LoggerFactory.getLogger(AdministradorDAOImpl.class);

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public Administrador save(Administrador administrador) {
        logger.info("DAO - Guardando administrador: {}", administrador.getUsuario());
        return administradorRepository.save(administrador);
    }

    @Override
    public Optional<Administrador> findById(Long id) {
        return administradorRepository.findById(id);
    }

    @Override
    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.warn("DAO - Eliminando administrador ID: {}", id);
        administradorRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return administradorRepository.existsById(id);
    }

    @Override
    public long count() {
        return administradorRepository.count();
    }

    @Override
    public Optional<Administrador> findByUsuario(String usuario) {
        logger.info("DAO - Buscando administrador por usuario: {}", usuario);
        return administradorRepository.findByUsuario(usuario);
    }

    @Override
    public boolean autenticar(String usuario, String password) {
        logger.info("DAO - Autenticando administrador: {}", usuario);
        return administradorRepository.findByUsuarioAndPassword(usuario, password).isPresent();
    }

    @Override
    public boolean existsByUsuario(String usuario) {
        return administradorRepository.existsByUsuario(usuario);
    }
}
