package org.example.user.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.example.user.domain.model.Address;
import org.example.user.domain.model.User;
import org.example.user.domain.port.spi.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(String userId) {
        return jpaUserRepository.findById(userId)
                .map(this::mapToUser);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = mapToUserEntity(user);
        return mapToUser(jpaUserRepository.save(userEntity));
    }

    @Override
    public void deleteById(String userId) {
        jpaUserRepository.deleteById(userId);
    }

    private User mapToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .addresses(mapToAddresses(userEntity.getAddresses()))
                .build();
    }

    private List<Address> mapToAddresses(List<AddressEntity> addressEntities) {
        if (addressEntities == null)
            return List.of();
        return addressEntities.stream()
                .map(this::mapToAddress)
                .collect(Collectors.toList());
    }

    private Address mapToAddress(AddressEntity addressEntity) {
        return Address.builder()
                .id(addressEntity.getId())
                .street(addressEntity.getStreet())
                .city(addressEntity.getCity())
                .state(addressEntity.getState())
                .postalCode(addressEntity.getPostalCode())
                .country(addressEntity.getCountry())
                .primaryAddress(addressEntity.isPrimaryAddress())
                .build();
    }

    private UserEntity mapToUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .addresses(mapToAddressEntities(user.getAddresses()))
                .build();
    }

    private List<AddressEntity> mapToAddressEntities(List<Address> addresses) {
        if (addresses == null)
            return List.of();
        return addresses.stream()
                .map(this::mapToAddressEntity)
                .collect(Collectors.toList());
    }

    private AddressEntity mapToAddressEntity(Address address) {
        return AddressEntity.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .primaryAddress(address.isPrimaryAddress())
                .build();
    }
}