package com.votacion.sistema_votacion.dao.interfaces;

import com.votacion.sistema_votacion.model.Administrador;
import java.util.Optional;

/**
 * Interfaz DAO para la entidad Administrador.
 */
public interface IAdministradorDAO extends IGenericDAO<Administrador, Long> {

    Optional<Administrador> findByUsuario(String usuario);

    boolean autenticar(String usuario, String password);

    boolean existsByUsuario(String usuario);
}
