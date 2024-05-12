package org.kst.storemgmtbackend.services;

import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.kst.storemgmtbackend.models.Item;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class CrudService<T,ID> {
    protected JpaRepository<T,ID> repository;

    CrudService(JpaRepository<T,ID> repository) {
        this.repository = repository;
    }

    public T save(@NotNull T entity) {
        return repository.save(entity);
    }

    public T findById(ID id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Data not found"));
    }

    public List<T> findAll(boolean paging, int limit, int offset) {
        if(!paging){
            return this.repository.findAll();
        }

        PageRequest pageRequest = PageRequest.of(offset, limit);
        return this.repository.findAll(pageRequest).getContent();
    }

    public T update(@NotNull T data) throws DataNotFoundException {
        return this.repository.save(data);
    }

    public void delete(@NotNull T item) throws DataNotFoundException {
        this.repository.delete(item);
    }

    public void deleteById(ID id) throws DataNotFoundException {
        this.repository.deleteById(id);
    }
}
