package org.example.user.domain.port.api;

import org.example.user.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers(int limit, int offset);
    Optional<User> getUserById(String userId);
    User createUser(User user);
    User updateUser(String userId, User user);
    void deleteUser(String userId);
} 