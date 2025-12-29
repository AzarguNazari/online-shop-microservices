package org.example.user.domain.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private boolean primaryAddress;
}
