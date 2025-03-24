package org.example.review.domain.port.primary;

import org.example.review.domain.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getAllReviews();
    Review createReview(Review review);
    Optional<Review> getReviewById(String reviewId);
    Optional<Review> updateReview(String reviewId, Review review);
    void deleteReview(String reviewId);
    Optional<Review> markReviewAsHelpful(String reviewId);
} 