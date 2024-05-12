package org.kst.storemgmtbackend.repositories;

import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, ObjectId> {
}