package org.kst.storemgmtbackend.models;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetails {
    private Set<String> contactEmail;
    private Set<String> phoneNumber;
}
