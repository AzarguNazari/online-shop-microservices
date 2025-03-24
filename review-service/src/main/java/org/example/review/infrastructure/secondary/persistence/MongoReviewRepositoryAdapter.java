package org.example.review.infrastructure.secondary.persistence;

import lombok.RequiredArgsConstructor;
import org.example.review.domain.model.Review;
import org.example.review.domain.port.secondary.ReviewRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MongoReviewRepositoryAdapter implements ReviewRepository {
    private final MongoReviewRepository mongoRepository;

    @Override
    public List<Review> findAll() {
        return mongoRepository.findAll().stream()
                .map(this::mapToReview)
                .collect(Collectors.toList());
    }

    @Override
    public Review save(Review review) {
        ReviewEntity entity = mapToEntity(review);
        return mapToReview(mongoRepository.save(entity));
    }

    @Override
    public Optional<Review> findById(String reviewId) {
        return mongoRepository.findById(reviewId)
                .map(this::mapToReview);
    }

    @Override
    public void deleteById(String reviewId) {
        mongoRepository.deleteById(reviewId);
    }

    private Review mapToReview(ReviewEntity entity) {
        return Review.builder()
                .reviewId(entity.getReviewId())
                .productId(entity.getProductId())
                .userId(entity.getUserId())
                .rating(entity.getRating())
                .comment(entity.getComment())
                .reviewDate(entity.getReviewDate())
                .verifiedPurchase(entity.isVerifiedPurchase())
                .helpfulVotes(entity.getHelpfulVotes())
                .images(entity.getImages())
                .build();
    }

    private ReviewEntity mapToEntity(Review review) {
        return ReviewEntity.builder()
                .reviewId(review.getReviewId())
                .productId(review.getProductId())
                .userId(review.getUserId())
                .rating(review.getRating())
                .comment(review.getComment())
                .reviewDate(review.getReviewDate())
                .verifiedPurchase(review.isVerifiedPurchase())
                .helpfulVotes(review.getHelpfulVotes())
                .images(review.getImages())
                .build();
    }
} 