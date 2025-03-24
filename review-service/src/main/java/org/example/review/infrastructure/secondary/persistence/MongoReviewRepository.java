package org.example.review.infrastructure.secondary.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MongoReviewRepository extends MongoRepository<ReviewEntity, String> {
} 