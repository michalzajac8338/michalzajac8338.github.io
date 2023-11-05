package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.ReviewDto;
import com.michal.booksylikeapp.entity.Review;
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
    @GetMapping
    public ResponseEntity<ReviewDto> readReview(@PathVariable Long clientId,
                                                @PathVariable Long visitId){

        ReviewDto reviewDto = reviewService.readReviewForAVisit(visitId);

        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }


    // Update
    @PutMapping
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long clientId,
                                                  @PathVariable Long visitId,
                                                  @RequestBody ReviewDto reviewDto){

        reviewDto.setReviewAndVisitId(visitId);
        ReviewDto updatedReview = reviewService.updateReview(reviewDto);

        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<Void> updateReview(@PathVariable Long clientId,
                                                  @PathVariable Long visitId){

        reviewService.deleteReview(visitId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
