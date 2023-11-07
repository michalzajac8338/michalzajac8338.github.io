package com.michal.booksylikeapp.contoller.publicRequests;

import com.michal.booksylikeapp.dto.ReviewDto;
import com.michal.booksylikeapp.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("B/review")
@AllArgsConstructor
public class ReviewController {

    private ReviewService reviewService;

    @GetMapping("/enterprise/enterpriseId={enterpriseId}")
    public ResponseEntity<List<ReviewDto>> readEnterpriseReviews(@PathVariable Long enterpriseId){

        List<ReviewDto> reviews = reviewService.readReviewsForEnterprise(enterpriseId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/employee/employeeId={employeeId}")
    public ResponseEntity<List<ReviewDto>> readEmployeeReviews(@PathVariable Long employeeId){

        List<ReviewDto> reviews = reviewService.readReviewsForEmployee(employeeId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
