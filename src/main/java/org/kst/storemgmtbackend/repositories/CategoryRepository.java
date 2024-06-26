package org.kst.storemgmtbackend.repositories;

import org.kst.storemgmtbackend.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Integer> {
}
