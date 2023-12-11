package sungjin.mybooks.domain.review.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.domain.common.annotation.AuthenticationRequired;
import sungjin.mybooks.domain.review.service.CommentService;
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
    private final CommentService commentService;

    @AuthenticationRequired
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

    @AuthenticationRequired
    @GetMapping("/review-create")
    public String reviewCreateForm(Model model, @RequestParam String isbn) {
        Book book = bookService.findOrCreateBook(isbn);
        model.addAttribute("book", new BookResponse(book));
        return "review-create";
    }

    @AuthenticationRequired
    @PostMapping("/review")
    public String createReview(@RequestParam Long bookId, String content, UserSession userSession) {
        Long userId = userSession.getUserId();
        Long id = reviewService.writeReview(userId, bookId, content);
        return "redirect:/reviews/" + id;
    }

    @AuthenticationRequired
    @GetMapping("/review/edit")
    public String editReviewForm(@RequestParam Long id, Model model) {
        Review review = reviewService.findReview(id);
        model.addAttribute("review", new ReviewModel(review));
        return "review-edit";
    }

    @AuthenticationRequired
    @PostMapping("/review/edit")
    public String editReview(@RequestParam Long id, String content, UserSession userSession) {
        reviewService.editReview(id, userSession.getUserId(), content);
        return "redirect:/reviews/" + id;
    }

    @AuthenticationRequired
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, UserSession userSession) {
        reviewService.removeReview(id, userSession.getUserId());
        return ResponseEntity.noContent().build();
    }

    @AuthenticationRequired
    @PostMapping("/reviews/{id}/comment")
    public String createComment(@PathVariable Long id, String comment, UserSession userSession){
        System.out.println("comment = " + comment);
        commentService.addComment(userSession.getUserId(),id, comment);
        return "redirect:/reviews/" + id;
    }

    @AuthenticationRequired
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> removeComment(@PathVariable Long id, UserSession userSession){
        commentService.removeComment(userSession.getUserId(),id);
        return ResponseEntity.noContent().build();
    }

    @AuthenticationRequired
    @PostMapping("/reviews/{id}/like")
    public ResponseEntity<Void> likeReview(@PathVariable Long id, UserSession userSession) {
        likeService.likeReview(userSession.getUserId(), id);
        return ResponseEntity.created(URI.create("/reviews/" + id))
                .build();
    }

    @AuthenticationRequired
    @DeleteMapping("/reviews/{id}/like")
    public ResponseEntity<Void> deleteLikeReview(@PathVariable Long id, UserSession userSession) {
        likeService.cancelLikeReview(userSession.getUserId(), id);
        return ResponseEntity.noContent().build();
    }
}
