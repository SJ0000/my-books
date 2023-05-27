package sungjin.mybooks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/review/{id}")
    public Review getReview(@PathVariable Long id){
        return reviewService.findReview(id);
    }



}
