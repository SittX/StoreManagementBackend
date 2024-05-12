package org.kst.storemgmtbackend.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String token;
    private String status;
    private String message;
}
