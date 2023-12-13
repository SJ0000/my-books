package sungjin.mybooks.domain.review.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.book.service.BookService;
import sungjin.mybooks.domain.common.annotation.DomainAuthorize;
import sungjin.mybooks.domain.common.model.PageModel;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.review.model.ReviewModel;
import sungjin.mybooks.domain.review.repository.ReviewRepository;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.service.UserService;
import sungjin.mybooks.global.exception.NotFound;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final BookService bookService;

    private final ReviewRepository reviewRepository;

    @Value("${app.page-size}")
    private int pageSize;

    @Transactional(readOnly = true)
    public PageModel<ReviewModel> findRecentReviews(Long userId, int page) {
        Page<Review> result = reviewRepository.findRecentReviews(userId, PageRequest.of(page, pageSize));

        List<ReviewModel> data = result.stream()
                .map(ReviewModel::new)
                .toList();

        return PageModel.<ReviewModel>builder()
                .data(data)
                .currentPage(page)
                .totalPage(result.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    public PageModel<ReviewModel> findReviewsByBookTitle(Long userId, String title, int page) {
        Page<Review> result = reviewRepository.findAllByBookTitle(userId, title, PageRequest.of(page, pageSize));

        List<ReviewModel> data = result.stream()
                .map(ReviewModel::new)
                .toList();

        return PageModel.<ReviewModel>builder()
                .data(data)
                .totalPage(result.getTotalPages())
                .currentPage(page)
                .build();
    }

    @Transactional
    public Long writeReview(Long userId, Long bookId, String content) {
        User user = userService.findUserById(userId);
        Book book = bookService.findBookById(bookId);

        Review review = Review.builder()
                .user(user)
                .book(book)
                .content(content)
                .build();

        reviewRepository.save(review);
        return review.getId();
    }

    @Transactional(readOnly = true)
    public Review findReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NotFound(Review.class, "id", id));
    }

    @Transactional
    @DomainAuthorize(Review.class)
    public void editReview(Long reviewId, Long userId, String content) {
        Review review = findReview(reviewId);
        review.editContent(content);
        System.out.println("content = " + review.getContent());
    }

    @Transactional
    @DomainAuthorize(Review.class)
    public void removeReview(Long reviewId, Long userId) {
        reviewRepository.deleteById(reviewId);
    }
}
