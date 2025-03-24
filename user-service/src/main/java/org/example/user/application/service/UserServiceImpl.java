package org.example.user.application.service;

import lombok.RequiredArgsConstructor;
import org.example.user.domain.model.User;
import org.example.user.domain.port.api.UserService;
import org.example.user.domain.port.spi.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers(int limit, int offset) {
        List<User> allUsers = userRepository.findAll();
        int endIndex = Math.min(offset + limit, allUsers.size());
        return allUsers.subList(offset, endIndex);
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String userId, User user) {
        return userRepository.save(User.builder()
                .id(userId)
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .addresses(user.getAddresses())
                .build());
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
} 