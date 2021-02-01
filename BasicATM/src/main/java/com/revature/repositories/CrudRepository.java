package com.revature.repositories;

import com.revature.utilities.LinkedList;

/*

 */
public interface CrudRepository<T> {

    void save(T newObj);

    LinkedList<T> findAll();

    T findById(int id);

    boolean update(T updatedObj);
    boolean deleteById(int id);

}
