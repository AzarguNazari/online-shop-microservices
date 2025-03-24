package org.example.user.domain.port.spi;

import org.example.user.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(String userId);
    User save(User user);
    void deleteById(String userId);
} 