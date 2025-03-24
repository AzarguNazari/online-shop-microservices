package org.example.user.domain.model;

import lombok.Builder;
import lombok.Value;
import java.util.List;

@Value
@Builder
public class User {
    String id;
    String name;
    String email;
    String phoneNumber;
    List<Address> addresses;
} 