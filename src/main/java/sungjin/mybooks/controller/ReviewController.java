package sungjin.mybooks.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.annotation.AuthRequired;
import sungjin.mybooks.config.data.UserSession;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.dto.response.ReviewResponse;
import sungjin.mybooks.service.BookService;
import sungjin.mybooks.service.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;

    @AuthRequired
    @GetMapping("/reviews")
    public String userReviews(UserSession userSession, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "1") int page, Model model) {
        Long userId = userSession.getUserId();

        if (StringUtils.hasText(query)) {
            PageResponse<ReviewResponse> result = reviewService.findReviewsByBookTitle(userId, query, page - 1);
            model.addAttribute("reviews", result.getData());
            model.addAttribute("page", result.getPageInfo());
        }

        if (!StringUtils.hasText(query)) {
            PageResponse<ReviewResponse> result = reviewService.findRecentReviews(userId, page - 1);
            model.addAttribute("reviews", result.getData());
            model.addAttribute("page", result.getPageInfo());
        }

        return "review-list";
    }

    @GetMapping("/reviews/{id}")
    public String getReview(@PathVariable Long id, Model model) {
        Review review = reviewService.findReview(id);
        model.addAttribute("review", new ReviewResponse(review));
        return "review-read";
    }

    @AuthRequired
    @GetMapping("/review-create")
    public String createReviewForm(Model model, @RequestParam String isbn) {
        Book book = bookService.findOrCreateBook(isbn);
        model.addAttribute("book", new BookResponse(book));
        return "review-create";
    }

    @AuthRequired
    @PostMapping("/review")
    public String createReview(@RequestParam Long bookId, String content, UserSession userSession) {
        Long userId = userSession.getUserId();
        Long id = reviewService.writeReview(userId, bookId, content);
        return "redirect:/reviews/" + id;
    }

    @AuthRequired
    @GetMapping("/review/edit")
    public String editReviewForm(@RequestParam Long id, Model model) {
        Review review = reviewService.findReview(id);
        model.addAttribute("review", new ReviewResponse(review));
        return "review-edit";
    }

    @AuthRequired
    @PostMapping("/review/edit")
    public String editReview(@RequestParam Long id, String content, UserSession userSession) {
        reviewService.editReview(id, userSession.getUserId(), content);
        return "redirect:/reviews/" + id;
    }

    @AuthRequired
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, UserSession userSession){
        reviewService.removeReview(id, userSession.getUserId());
        return ResponseEntity.noContent().build();
    }

}
