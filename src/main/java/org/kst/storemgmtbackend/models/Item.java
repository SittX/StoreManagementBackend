package org.kst.storemgmtbackend.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "items")
public class Item {
    @MongoId
    private ObjectId id;
    private String shortDescription;
    private String longDescription;
    private float price;
    private long availableUnit;
    private List<String> imageUrlList;

    @DocumentReference(lazy = true)
    private List<Category> category;
}
