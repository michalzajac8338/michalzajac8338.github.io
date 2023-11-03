package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.ReviewDto;
import com.michal.booksylikeapp.entity.Review;

public class ReviewMapper {

    public static Review mapToReview(ReviewDto reviewDto, Review review){

        if(review==null) {
            review = new Review();
        }

        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());

        return review;
    }

    public static ReviewDto mapToReviewDto(Review review){

        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setReviewAndVisitId(review.getId());
        reviewDto.setContent(review.getContent());
        reviewDto.setRating(review.getRating());

        return  reviewDto;
    }
}
