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
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private VisitRepository visitRepository;
    private ReviewRepository reviewRepository;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {

        Review review = ReviewMapper.mapToReview(reviewDto, null);
        Visit visit = visitRepository.findById(reviewDto.getReviewAndVisitId()).orElseThrow(RuntimeException::new);

        review.setVisit(visit);
        visit.setReview(review);

        Review savedReview = reviewRepository.save(review);

        return ReviewMapper.mapToReviewDto(savedReview);
    }

    @Override
    public ReviewDto readReviewForAVisit(Long visitId) {

        Review review = reviewRepository.findById(visitId).orElse(null);
        return ReviewMapper.mapToReviewDto(review);
    }

    @Override
    public ReviewDto updateReview(ReviewDto reviewDto) {

        Review review = reviewRepository.findById(reviewDto.getReviewAndVisitId()).orElseThrow(RuntimeException::new);
        Review updatedReview = ReviewMapper.mapToReview(reviewDto, review);
        Review savedReview = reviewRepository.save(updatedReview);

        return ReviewMapper.mapToReviewDto(savedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Long visitId) {

        visitRepository.findById(visitId).orElseThrow(RuntimeException::new).setReview(null);
        reviewRepository.deleteById(visitId);

    }
}
