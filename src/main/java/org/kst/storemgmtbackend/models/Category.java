package org.kst.storemgmtbackend.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "categories")
public class Category {
    @MongoId
    private ObjectId id;
    private String name;
    private String description;
    private String imageUrl;
}
