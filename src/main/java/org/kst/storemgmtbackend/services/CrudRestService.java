package org.kst.storemgmtbackend.services;

import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 *  Interface that gather some common CRUD operations to enforce the service classes to follow the structure
 * @param <T> Basic Object Type
 * @param <ID> Object ID type
 */
public interface CrudRestService<T, ID> {
    T save(T t);

    T findById(ID id) throws DataNotFoundException;

    List<T> findAll(boolean paging, int limit, int offset);

    T update(T t) throws DataNotFoundException;

    void delete(T t) throws DataNotFoundException;

    void deleteById(ID id) throws DataNotFoundException;
}
