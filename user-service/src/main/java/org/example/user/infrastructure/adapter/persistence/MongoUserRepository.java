package org.example.user.infrastructure.adapter.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MongoUserRepository extends MongoRepository<UserEntity, String> {
} 