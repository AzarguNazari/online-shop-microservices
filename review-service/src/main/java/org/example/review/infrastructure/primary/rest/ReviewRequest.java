package org.example.review.infrastructure.primary.rest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private String productId;
    private String userId;
    private int rating;
    private String comment;
    private boolean verifiedPurchase;
    private List<String> images;
} 