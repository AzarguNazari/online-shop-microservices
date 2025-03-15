package org.example.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class User {
    @Id
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Address> addresses;
}

@Document(collection = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Address {
    @Id
    private String addressId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private boolean isPrimary;
}

interface UserRepository extends MongoRepository<User, String> {}

interface AddressRepository extends MongoRepository<Address, String> {}

@Service
class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(int limit, int offset) {
        return userRepository.findAll().subList(offset, Math.min(offset + limit, userRepository.findAll().size()));
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String userId, User user) {
        user.setUserId(userId);
        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}

@Service
class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getUserAddresses(String userId) {
        return addressRepository.findAll();
    }

    public Address addAddress(String userId, Address address) {
        return addressRepository.save(address);
    }
}

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(userService.getAllUsers(limit, offset));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}

@RestController
@RequestMapping("/users/{userId}/addresses")
class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable String userId) {
        return ResponseEntity.ok(addressService.getUserAddresses(userId));
    }

    @PostMapping
    public ResponseEntity<Address> addAddress(@PathVariable String userId, @RequestBody Address address) {
        return ResponseEntity.status(201).body(addressService.addAddress(userId, address));
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ErrorResponse {
    private int code;
    private String message;
}

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(500, ex.getMessage()));
    }
}
