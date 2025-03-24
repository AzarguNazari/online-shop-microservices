package org.example.user.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Address {
    String id;
    String street;
    String city;
    String state;
    String postalCode;
    String country;
    boolean primary;
} 