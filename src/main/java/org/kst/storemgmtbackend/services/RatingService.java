package org.kst.storemgmtbackend.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.kst.storemgmtbackend.models.Rating;
import org.kst.storemgmtbackend.repositories.RatingRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService implements CrudRestService<Rating, ObjectId> {
    private final RatingRepository ratingRepository;

    @Override
    public Rating save(@NotNull Rating rating) {
        return this.ratingRepository.save(rating);
    }

    @Override
    public Rating findById(ObjectId objectId) {
        return this.ratingRepository
                .findById(objectId)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
    }

    @Override
    public List<Rating> findAll(boolean paging, int limit, int offset) {
        if(!paging){
            return this.ratingRepository.findAll();
        }

        PageRequest pageRequest = PageRequest.of(offset, limit);
        return this.ratingRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Rating update(@NotNull Rating rating) throws DataNotFoundException {
        if(!ratingRepository.existsById(rating.getId())){
            throw new DataNotFoundException("Rating not found");
        }

        return this.ratingRepository.save(rating);
    }

    @Override
    public void delete(@NotNull Rating rating) throws DataNotFoundException {
        if(!ratingRepository.existsById(rating.getId())){
            throw new DataNotFoundException("Rating not found");
        }

        this.ratingRepository.delete(rating);
    }

    @Override
    public void deleteById(ObjectId objectId) throws DataNotFoundException {
        if(!ratingRepository.existsById(objectId)){
            throw new DataNotFoundException("Rating not found");
        }

        this.ratingRepository.deleteById(objectId);
    }

}
