package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final BookService bookService;

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public PageResponse<BookResponse> findRecentReviews(Long userId, int page){
        Page<Review> result = reviewRepository.findRecentReviews(userId, PageRequest.of(page, 10));

        List<BookResponse> data = result.stream()
                .map(userBook -> new BookResponse(userBook.getBook()))
                .toList();

        return PageResponse.<BookResponse>builder()
                .data(data)
                .currentPage(page)
                .totalPage(result.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponse<BookResponse> findReviewsByBookTitle(Long userId, String title, int page){
        Page<Review> result = reviewRepository.findAllByBookTitle(userId,title, PageRequest.of(page,10));

        List<BookResponse> data = result.stream()
                .map(userBook -> new BookResponse(userBook.getBook()))
                .toList();

        return PageResponse.<BookResponse>builder()
                .data(data)
                .totalPage(result.getTotalPages())
                .currentPage(page)
                .build();
    }

    @Transactional
    public Long writeReview(Long userId, ReviewCreate reviewCreate){
        // Review 생성
        Long bookId = reviewCreate.getBookId();

        User user = userService.findUser(userId);
        Book book = bookService.findBook(bookId);

        Review review = Review.builder()
                .user(user)
                .book(book)
                .content(reviewCreate.getContent())
                .build();

        reviewRepository.save(review);
        return review.getId();
    }

    @Transactional(readOnly = true)
    public Review findReview(Long id){
        return reviewRepository.findById(id)
                .orElseThrow( ()-> new NotFound(Review.class, "id", id));
    }

    @Transactional
    public void editReview(Long id, String content){
        Review review = findReview(id);
        review.editContent(content);
    }

}
