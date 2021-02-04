package com.revature.repositories;

import com.revature.utilities.LinkedList;

/**
 * CrudRepository is an interface to standardize Repository access
 * to the database
 * <p>
 * @param <T>   The object type representing the database tables being accessed
 * @author Wezley Singleton
 */
public interface CrudRepository<T> {

    void save(T newObj);

    LinkedList<T> findAll();

    T findById(int id);

    boolean update(T updatedObj);
    boolean deleteById(int id);

}
