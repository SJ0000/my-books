package sungjin.mybooks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.dto.request.ReviewEdit;
import sungjin.mybooks.service.ReviewService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/review/{id}")
    public Review getReview(@PathVariable Long id){
        return reviewService.findReview(id);
    }

    @PostMapping("/review")
    public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewCreate reviewCreate){
        // todo : userId 받아오기
        Long id = reviewService.writeReview(null, reviewCreate);

        return ResponseEntity.created(URI.create("/review/"+id))
                .build();
    }

    @PatchMapping("/review/{id}")
    public ResponseEntity<Void> editReview(@PathVariable Long id, @RequestBody @Valid ReviewEdit reviewEdit){
        reviewService.editReview(id, reviewEdit.getContent());
        return ResponseEntity.created(URI.create("/review/"+id))
                .build();
    }

}
