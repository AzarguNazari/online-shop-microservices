package org.example.review;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ReviewServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReviewServiceApplication.class, args);
    }
}

@Document(collection = "reviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Review {
    @Id
    private String reviewId;
    private String productId;
    private String userId;
    private int rating;
    private String comment;
    private LocalDateTime reviewDate;
    private boolean verifiedPurchase;
    private int helpfulVotes;
    private List<String> images;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ReviewCreateRequest {
    private String productId;
    private String userId;
    private int rating;
    private String comment;
    private boolean verifiedPurchase;
    private List<String> images;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ReviewUpdateRequest {
    private int rating;
    private String comment;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Error {
    private int code;
    private String message;
}

@Repository
interface ReviewRepository extends MongoRepository<Review, String> {
}

@Service
@RequiredArgsConstructor
class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review createReview(ReviewCreateRequest request) {
        Review review = Review.builder()
            .productId(request.getProductId())
            .userId(request.getUserId())
            .rating(request.getRating())
            .comment(request.getComment())
            .reviewDate(LocalDateTime.now())
            .verifiedPurchase(request.isVerifiedPurchase())
            .images(request.getImages())
            .helpfulVotes(0)
            .build();
        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(String reviewId) {
        return reviewRepository.findById(reviewId);
    }

    public Optional<Review> updateReview(String reviewId, ReviewUpdateRequest request) {
        return reviewRepository.findById(reviewId).map(review -> {
            review.setRating(request.getRating());
            review.setComment(request.getComment());
            return reviewRepository.save(review);
        });
    }

    public void deleteReview(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public Optional<Review> markReviewAsHelpful(String reviewId) {
        return reviewRepository.findById(reviewId).map(review -> {
            review.setHelpfulVotes(review.getHelpfulVotes() + 1);
            return reviewRepository.save(review);
        });
    }
}

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewCreateRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Error(HttpStatus.BAD_REQUEST.value(), "Invalid input data"));
        }
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable String reviewId) {
        return reviewService.getReviewById(reviewId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable String reviewId, @RequestBody ReviewUpdateRequest request) {
        return reviewService.updateReview(reviewId, request)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{reviewId}/helpful")
    public ResponseEntity<?> markReviewAsHelpful(@PathVariable String reviewId) {
        return reviewService.markReviewAsHelpful(reviewId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
