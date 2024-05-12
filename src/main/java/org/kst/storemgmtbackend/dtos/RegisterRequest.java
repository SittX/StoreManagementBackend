package org.kst.storemgmtbackend.dtos;

import lombok.*;
import org.kst.storemgmtbackend.models.Profile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Profile profile;
}
