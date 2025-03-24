package org.example.review.domain.port.secondary;

import org.example.review.domain.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    List<Review> findAll();
    Review save(Review review);
    Optional<Review> findById(String reviewId);
    void deleteById(String reviewId);
} 