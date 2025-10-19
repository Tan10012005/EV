package org.example.be.controller;

import org.example.be.dto.reponse.ApiResponse;
import org.example.be.dto.reponse.MemberResponse;
import org.example.be.dto.reponse.ReviewResponse;
import org.example.be.entity.Review;
import org.example.be.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> createReview(@RequestBody Review review) {
        ApiResponse<Review> response = new ApiResponse<>();
        try {
            Review createdReview = reviewService.createReview(review);

            HashMap<String, Object> metadata = new HashMap<>();
            metadata.put("timestamp", LocalDateTime.now());

            response.ok(createdReview, metadata);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());

            response.error(error);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Review>>> getAllReviews() {
        ApiResponse<List<Review>> response = new ApiResponse<>();
        try {
            List<Review> reviews = reviewService.getAllReviews();

            HashMap<String, Object> metadata = new HashMap<>();
            metadata.put("count", reviews.size());
            metadata.put("timestamp", LocalDateTime.now());

            response.ok(reviews, metadata);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());

            response.error(error);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(@PathVariable Integer id) {
        ApiResponse<ReviewResponse> response = new ApiResponse<>();
        try {
            Review review = reviewService.getReviewById(id);
            if (review != null) {
                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setReviewId(review.getReviewsId());
                reviewResponse.setComment(review.getComment());
                reviewResponse.setRating(review.getRating());
                reviewResponse.setCreatedAt(review.getCreatedAt());
                HashMap<String, Object> metadata = new HashMap<>();
                metadata.put("timestamp", LocalDateTime.now());
                response.ok(reviewResponse, metadata);
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Review not found");
                response.error(error);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            response.error(error);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Review>> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        ApiResponse<Review> response = new ApiResponse<>();
        try {
            Review updatedReview = reviewService.updateReview(id, review);
            if (updatedReview != null) {
                HashMap<String, Object> metadata = new HashMap<>();
                metadata.put("updatedAt", LocalDateTime.now());
                response.ok(updatedReview, metadata);
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Review not found");
                response.error(error);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            response.error(error);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteReview(@PathVariable Integer id) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
          reviewService.deleteReview(id);

                response.ok();
                return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            response.error(error);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> findAllReviewBySellerId(@PathVariable Integer sellerId) {
        ApiResponse<List<ReviewResponse>> response = new ApiResponse<>();
        try {
            List<Review> reviews = reviewService.findAllReviewBySellerId(sellerId);
            List<ReviewResponse> reviewResponses = reviews.stream().map(review -> {
                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setReviewId(review.getReviewsId());
                reviewResponse.setComment(review.getComment());
                reviewResponse.setRating(review.getRating());
                reviewResponse.setCreatedAt(review.getCreatedAt());
                MemberResponse reviewer = new MemberResponse();
                reviewer.setMemberId(review.getReviewer().getMemberId());
                reviewer.setUsername(review.getReviewer().getUsername());
                reviewer.setAvatarUrl(review.getReviewer().getAvatarUrl());
                reviewResponse.setReviewer(reviewer);
                return reviewResponse;
            }).toList();
            response.ok(reviewResponses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            response.error(error);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
