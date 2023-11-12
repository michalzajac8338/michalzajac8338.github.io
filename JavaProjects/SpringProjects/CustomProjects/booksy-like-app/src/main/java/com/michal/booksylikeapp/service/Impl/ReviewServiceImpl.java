package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.ReviewDto;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.entity.Enterprise;
import com.michal.booksylikeapp.entity.Review;
import com.michal.booksylikeapp.entity.Visit;
import com.michal.booksylikeapp.mapper.ReviewMapper;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.repository.EnterpriseRepository;
import com.michal.booksylikeapp.repository.ReviewRepository;
import com.michal.booksylikeapp.repository.VisitRepository;
import com.michal.booksylikeapp.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private EnterpriseRepository enterpriseRepository;
    private VisitRepository visitRepository;
    private ReviewRepository reviewRepository;
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public ReviewDto createReview(ReviewDto reviewDto) {

        Review review = ReviewMapper.mapToReview(reviewDto, null);
        Visit visit = visitRepository.findById(reviewDto.getReviewAndVisitId()).orElseThrow(RuntimeException::new);
        Employee employee = employeeRepository.findById(visit.getWorkday().getEmployee().getId()).orElseThrow(RuntimeException::new);
        review.setVisit(visit);
        review.setEmployee(employee);
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

    @Override
    public List<Object> readReviewsForEnterprise(Long enterpriseId) {

        Enterprise enterprise = enterpriseRepository.findById(enterpriseId).orElseThrow(RuntimeException::new);
        List<Review> reviews = new LinkedList<>();

        enterprise.getEmployees().forEach(employee -> reviews.addAll(employee.getReviews()));

        double averageRatingValue = 0.0;

        if(!reviews.isEmpty()) {
            averageRatingValue = ((double) reviews.stream().map(Review::getRating).reduce(0, Integer::sum)) / reviews.size();
        }

        List<ReviewDto> reviewDtos = reviews.stream().map(ReviewMapper::mapToReviewDto).toList();
        List<Object> reviewAndAverageRating = new LinkedList<>();

        reviewAndAverageRating.add(Collections.singletonMap("averageRating", averageRatingValue));
        reviewAndAverageRating.add(reviewDtos);

        return reviewAndAverageRating;
    }

    @Override
    public List<Object> readReviewsForEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        List<Review> reviews = employee.getReviews();

        double averageRatingValue = 0.0;

        if(!reviews.isEmpty()) {
            averageRatingValue = ((double) reviews.stream().map(Review::getRating).reduce(0, Integer::sum)) / reviews.size();
        }
        List<ReviewDto> reviewDtos = reviews.stream().map(ReviewMapper::mapToReviewDto).toList();

        List<Object> reviewAndAverageRating = new LinkedList<>();
        reviewAndAverageRating.add(Collections.singletonMap("averageRating", averageRatingValue));
        reviewAndAverageRating.add(reviewDtos);

        return reviewAndAverageRating;
    }
}
