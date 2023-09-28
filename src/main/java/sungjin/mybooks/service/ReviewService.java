package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.exception.Unauthorized;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final BookService bookService;

    private final ReviewRepository reviewRepository;

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
