package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ReviewDto;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);

    ReviewDto readReviewForAVisit(Long visitId);

    ReviewDto updateReview(ReviewDto reviewDto);

    void deleteReview(Long visitId);
}
