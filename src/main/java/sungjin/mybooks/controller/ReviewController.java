package sungjin.mybooks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.annotation.AuthRequired;
import sungjin.mybooks.config.data.UserSession;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.dto.request.ReviewEdit;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.dto.response.ReviewResponse;
import sungjin.mybooks.service.ReviewService;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @AuthRequired
    @GetMapping("/reviews")
    public String userReviews(UserSession userSession, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "1") int page, Model model){
        Long userId = userSession.getUserId();

        if(StringUtils.hasText(query)){
            PageResponse<BookResponse> result = reviewService.findReviewsByBookTitle(userId, query, page);
            model.addAttribute("books", result.getData());
            model.addAttribute("page", result.getPageInfo());
        }

        if(!StringUtils.hasText(query)){
            PageResponse<BookResponse> result = reviewService.findRecentReviews(userId, page);
            model.addAttribute("books", result.getData());
            model.addAttribute("page", result.getPageInfo());
        }

        return "review-list";
    }

    @GetMapping("/review/{id}")
    public String getReview(@PathVariable Long id, Model model){
        Review review = reviewService.findReview(id);
        return "review-read";
    }

    @AuthRequired
    @GetMapping
    public String createReviewForm(Model model){
        return "review-create";
    }

    @AuthRequired
    @PostMapping("/review")
    public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewCreate reviewCreate, UserSession userSession){
        Long userId = userSession.getUserId();
        Long id = reviewService.writeReview(userId, reviewCreate);

        return ResponseEntity.created(URI.create("/review/"+id))
                .build();
    }

    @AuthRequired
    @PatchMapping("/review/{id}")
    public ResponseEntity<Void> editReview(@PathVariable Long id, @RequestBody @Valid ReviewEdit reviewEdit){
        reviewService.editReview(id, reviewEdit.getContent());
        return ResponseEntity.created(URI.create("/review/"+id))
                .build();
    }

}
