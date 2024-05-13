package org.kst.storemgmtbackend.repositories;

import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<Rating, ObjectId> {
    Optional<List<Rating>> findByUser_Id(ObjectId id);
    Optional<List<Rating>> findByItem_Id(ObjectId id);
}
