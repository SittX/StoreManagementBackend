package org.kst.storemgmtbackend.repositories;

import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.models.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends MongoRepository<Store, ObjectId> {
}
