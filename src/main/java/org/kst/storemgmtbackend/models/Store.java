package org.kst.storemgmtbackend.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "stores")
public class Store {
    @MongoId
    private ObjectId id;
    private String description;
    private String remark;
    private ContactDetails contactDetails;
    private Geolocation geolocation;
    private Address address;
    private LocalDateTime approvedDate;
    private boolean isApproved;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @DocumentReference(lazy = true)
    private Set<User> owners;

    @DocumentReference(lazy = true)
    private Set<Item> items;
}
