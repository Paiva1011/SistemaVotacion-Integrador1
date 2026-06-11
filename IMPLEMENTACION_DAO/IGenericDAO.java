package com.votacion.sistema_votacion.dao.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica DAO que define las operaciones CRUD básicas.
 * Todas las interfaces DAO específicas extienden de esta.
 *
 * @param <T>  
 * @param <ID> 
 */
public interface IGenericDAO<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    boolean existsById(ID id);

    long count();
}
