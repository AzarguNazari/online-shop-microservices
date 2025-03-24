package org.example.review.infrastructure.secondary.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "reviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
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