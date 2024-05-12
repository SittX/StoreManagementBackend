package org.kst.storemgmtbackend.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Geolocation {
    private String latitude;
    private String longitude;
}
