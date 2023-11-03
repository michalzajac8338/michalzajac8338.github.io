package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.ReviewDto;
import com.michal.booksylikeapp.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("B/client/clientId={clientId}/visit/visitId={visitId}/review")
@AllArgsConstructor
public class ReviewController {

    private ReviewService reviewService;

    // CRUD for visits
    // Create
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@PathVariable Long clientId,
                                                  @PathVariable Long visitId,
                                                  @RequestBody ReviewDto reviewDto){

        reviewDto.setReviewAndVisitId(visitId);
        ReviewDto createdReview = reviewService.createReview(reviewDto);

        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    // Read
//    @GetMapping("reviewId={reviewId}")
//    public ResponseEntity<ReviewDto> readReview(@RequestParam)
}
