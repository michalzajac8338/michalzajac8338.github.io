package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);

    ReviewDto readReviewForAVisit(Long visitId);

    ReviewDto updateReview(ReviewDto reviewDto);

    void deleteReview(Long visitId);

    List<ReviewDto> readReviewsForEnterprise(Long enterpriseId);

    List<ReviewDto> readReviewsForEmployee(Long employeeId);
}
