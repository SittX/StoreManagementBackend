package org.kst.storemgmtbackend.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private String firstname;
    private String lastname;
    private String avatarUrl;
}
