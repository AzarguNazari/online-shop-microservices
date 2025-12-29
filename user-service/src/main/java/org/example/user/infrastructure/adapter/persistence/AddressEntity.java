package org.example.user.infrastructure.adapter.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    @Column(name = "primary_address")
    private boolean primaryAddress;
}