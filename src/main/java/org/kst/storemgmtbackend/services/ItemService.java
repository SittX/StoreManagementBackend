package org.kst.storemgmtbackend.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.kst.storemgmtbackend.models.Item;
import org.kst.storemgmtbackend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService implements CrudRestService<Item,ObjectId> {
    private final ItemRepository itemRepository;

    @Override
    public Item save(@NotNull Item item) {
       return this.itemRepository.save(item);
    }

    @Override
    public Item findById(ObjectId objectId) {
        return this.itemRepository
                .findById(objectId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }

    @Override
    public List<Item> findAll(boolean paging, int limit, int offset) {
        if(!paging){
           return this.itemRepository.findAll();
        }

        PageRequest pageRequest = PageRequest.of(offset, limit);
        return this.itemRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Item update(@NotNull Item item) throws DataNotFoundException {
        if(!itemRepository.existsById(item.getId())){
           throw new DataNotFoundException("Item not found");
        }

        return this.itemRepository.save(item);
    }

    @Override
    public void delete(@NotNull Item item) throws DataNotFoundException {
        if(!itemRepository.existsById(item.getId())){
            throw new DataNotFoundException("Item not found");
        }

        this.itemRepository.delete(item);
    }

    @Override
    public void deleteById(ObjectId objectId) throws DataNotFoundException {
        if(!itemRepository.existsById(objectId)){
            throw new DataNotFoundException("Item not found");
        }

        this.itemRepository.deleteById(objectId);
    }
}
