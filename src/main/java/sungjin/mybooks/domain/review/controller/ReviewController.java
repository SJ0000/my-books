package sungjin.mybooks.domain.review.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.domain.common.annotation.AuthRequired;
import sungjin.mybooks.global.data.UserSession;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.book.model.BookResponse;
import sungjin.mybooks.domain.common.model.PageModel;
import sungjin.mybooks.domain.review.model.ReviewModel;
import sungjin.mybooks.domain.book.service.BookService;
import sungjin.mybooks.domain.review.service.LikeService;
import sungjin.mybooks.domain.review.service.ReviewService;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;
    private final LikeService likeService;

    @AuthRequired
    @GetMapping("/reviews")
    public String userReviews(UserSession userSession, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "1") int page, Model model) {
        Long userId = userSession.getUserId();

        if (StringUtils.hasText(query)) {
            PageModel<ReviewModel> result = reviewService.findReviewsByBookTitle(userId, query, page - 1);
            model.addAttribute("reviews", result.getData());
            model.addAttribute("page", result.getPageInfo());
        }

        if (!StringUtils.hasText(query)) {
            PageModel<ReviewModel> result = reviewService.findRecentReviews(userId, page - 1);
            model.addAttribute("reviews", result.getData());
            model.addAttribute("page", result.getPageInfo());
        }

        return "review-list";
    }

    @GetMapping("/reviews/{id}")
    public String getReview(@PathVariable Long id, Model model) {
        Review review = reviewService.findReview(id);
        model.addAttribute("review", new ReviewModel(review));
        return "review-read";
    }

    @AuthRequired
    @GetMapping("/review-create")
    public String reviewCreateForm(Model model, @RequestParam String isbn) {
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
        model.addAttribute("review", new ReviewModel(review));
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
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, UserSession userSession) {
        reviewService.removeReview(id, userSession.getUserId());
        return ResponseEntity.noContent().build();
    }

    @AuthRequired
    @PostMapping("/reviews/{id}/like")
    public ResponseEntity<Void> likeReview(@PathVariable Long id, UserSession userSession) {
        likeService.likeReview(userSession.getUserId(), id);
        return ResponseEntity.created(URI.create("/reviews/" + id))
                .build();
    }

    @AuthRequired
    @DeleteMapping("/reviews/{id}/like")
    public ResponseEntity<Void> deleteLikeReview(@PathVariable Long id, UserSession userSession) {
        likeService.cancelLikeReview(userSession.getUserId(), id);
        return ResponseEntity.noContent().build();
    }
}
