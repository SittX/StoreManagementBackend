package org.kst.storemgmtbackend.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private String remark;
}
