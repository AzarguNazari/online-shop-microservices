package org.example.review.application.service;

import lombok.RequiredArgsConstructor;
import org.example.review.domain.model.Review;
import org.example.review.domain.port.primary.ReviewService;
import org.example.review.domain.port.secondary.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> getReviewById(String reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Override
    public Optional<Review> updateReview(String reviewId, Review updatedReview) {
        return reviewRepository.findById(reviewId)
                .map(review -> reviewRepository.save(Review.builder()
                        .reviewId(review.getReviewId())
                        .productId(review.getProductId())
                        .userId(review.getUserId())
                        .rating(updatedReview.getRating())
                        .comment(updatedReview.getComment())
                        .reviewDate(review.getReviewDate())
                        .verifiedPurchase(review.isVerifiedPurchase())
                        .helpfulVotes(review.getHelpfulVotes())
                        .images(review.getImages())
                        .build()));
    }

    @Override
    public void deleteReview(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Optional<Review> markReviewAsHelpful(String reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> reviewRepository.save(Review.builder()
                        .reviewId(review.getReviewId())
                        .productId(review.getProductId())
                        .userId(review.getUserId())
                        .rating(review.getRating())
                        .comment(review.getComment())
                        .reviewDate(review.getReviewDate())
                        .verifiedPurchase(review.isVerifiedPurchase())
                        .helpfulVotes(review.getHelpfulVotes() + 1)
                        .images(review.getImages())
                        .build()));
    }
} 