package sungjin.mybooks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/review/{id}")
    public Review getReview(@PathVariable Long id){
        return reviewService.findReview(id);
    }

    @PostMapping("/review")
    public Review createReview(@RequestBody @Valid ReviewCreate reviewCreate){
        // todo : userId 받아오기
        Review review = reviewService.writeReview(null, reviewCreate);
        return review;
    }
}
