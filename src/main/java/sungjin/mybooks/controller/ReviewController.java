package sungjin.mybooks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.annotation.AuthRequired;
import sungjin.mybooks.config.data.UserSession;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.dto.request.ReviewEdit;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.dto.response.ReviewResponse;
import sungjin.mybooks.service.BookService;
import sungjin.mybooks.service.ReviewService;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;

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

        model.addAttribute("review", new ReviewResponse(review));

        return "review-read";
    }

    @AuthRequired
    @GetMapping("/review-create")
    public String createReviewForm(Model model, @RequestParam String isbn){
        Book book = bookService.findBookByIsbn(isbn);
        model.addAttribute("book", new BookResponse(book));
        return "review-create";
    }

    @AuthRequired
    @PostMapping("/review")
    public String createReview(@Valid ReviewCreate reviewCreate, @RequestParam Long bookId, UserSession userSession){

        Long userId = userSession.getUserId();
        Long id = reviewService.writeReview(userId, bookId,reviewCreate.getContent());

        return "redirect:/review/"+id;
    }

    @AuthRequired
    @PatchMapping("/review/{id}")
    public ResponseEntity<Void> editReview(@PathVariable Long id, @RequestBody @Valid ReviewEdit reviewEdit){
        reviewService.editReview(id, reviewEdit.getContent());
        return ResponseEntity.created(URI.create("/review/"+id))
                .build();
    }

}
