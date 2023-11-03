package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.ReviewDto;
import com.michal.booksylikeapp.entity.Review;
import com.michal.booksylikeapp.entity.Visit;
import com.michal.booksylikeapp.mapper.ReviewMapper;
import com.michal.booksylikeapp.repository.ReviewRepository;
import com.michal.booksylikeapp.repository.VisitRepository;
import com.michal.booksylikeapp.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private VisitRepository visitRepository;
    private ReviewRepository reviewRepository;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {

        Review review = ReviewMapper.mapToReview(reviewDto);
        Visit visit = visitRepository.findById(reviewDto.getReviewAndVisitId()).orElseThrow(RuntimeException::new);
        review.setVisit(visit);
        visit.setReview(review);
        Review savedReview = reviewRepository.save(review);

        return ReviewMapper.mapToReviewDto(savedReview);
    }
}
