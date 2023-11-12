package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    // CRUD
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto readReviewForAVisit(Long visitId);
    ReviewDto updateReview(ReviewDto reviewDto);
    void deleteReview(Long visitId);

    // Additionally
    List<Object> readReviewsForEnterprise(Long enterpriseId);
    List<Object> readReviewsForEmployee(Long employeeId);
}
