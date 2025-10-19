package org.example.be.service;

import org.example.be.entity.Review;
import org.example.be.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Integer id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    public Review getReviewByTransactionId(Integer transactionId) {
        return reviewRepository.findByTransaction_TransactionsId(transactionId).orElse(null);
    }

    public Review updateReview(Integer id, Review reviewDetails) {
        Review review = getReviewById(id);
        if (review == null) {
            return null;
        }
        review.setSeller(reviewDetails.getSeller());
        review.setReviewer(reviewDetails.getReviewer());
        review.setTransaction(reviewDetails.getTransaction());
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        review.setStatus(reviewDetails.getStatus());
        review.setCreatedAt(reviewDetails.getCreatedAt());
        return reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> findAllReviewBySellerId(Integer sellerId) {
        return reviewRepository.findAllBySeller_MemberId(sellerId);
    }
}
