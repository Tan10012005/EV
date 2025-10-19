package org.example.fe.service;

import org.example.fe.entity.ApiResponse;
import org.example.fe.entity.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ApiResponse<List<ReviewResponse>> getAllBuyerReview(int postId);
    ApiResponse<List<ReviewResponse>> FindAllReviewBySellerId(int sellerId);
}
