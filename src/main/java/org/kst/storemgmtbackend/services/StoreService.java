package org.kst.storemgmtbackend.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.kst.storemgmtbackend.models.Store;
import org.kst.storemgmtbackend.repositories.StoreRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService implements CrudRestService<Store, ObjectId> {
    private final StoreRepository storeRepository;

    @Override
    public Store save(@NotNull Store store) {
        return this.storeRepository.save(store);
    }

    @Override
    public Store findById(ObjectId objectId) {
        return this.storeRepository
                .findById(objectId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));
    }

    @Override
    public List<Store> findAll(boolean paging, int limit, int offset) {
        if(!paging){
            return this.storeRepository.findAll();
        }

        PageRequest pageRequest = PageRequest.of(offset, limit);
        return this.storeRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Store update(@NotNull Store store) throws DataNotFoundException {
        if(!storeRepository.existsById(store.getId())){
            throw new DataNotFoundException("Store not found");
        }

        return this.storeRepository.save(store);
    }

    @Override
    public void delete(@NotNull Store store) throws DataNotFoundException {
        if(!storeRepository.existsById(store.getId())){
            throw new DataNotFoundException("Store not found");
        }

        this.storeRepository.delete(store);
    }

    @Override
    public void deleteById(ObjectId objectId) throws DataNotFoundException {
        if(!storeRepository.existsById(objectId)){
            throw new DataNotFoundException("Store not found");
        }

        this.storeRepository.deleteById(objectId);
    }

}
