package org.example.review.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class Review {
    String reviewId;
    String productId;
    String userId;
    int rating;
    String comment;
    LocalDateTime reviewDate;
    boolean verifiedPurchase;
    int helpfulVotes;
    List<String> images;
} 