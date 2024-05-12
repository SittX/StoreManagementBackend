package org.kst.storemgmtbackend.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "ratings")
public class Rating {
    @MongoId
    private ObjectId id;

    private long rating;
    private String comment;

    @DocumentReference(lazy = true)
    private User user;

    @DocumentReference(lazy = true)
    private Item item;
}
