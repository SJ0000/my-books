package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final UserService userService;

    @Transactional
    public Review writeReview(Long userId, Long bookId, String content){
        // Review 생성
        User user = userService.findUser(userId);
        Book book = bookService.findBook(bookId);
        Review review = Review.builder()
                .user(user)
                .book(book)
                .content(content)
                .build();

        return reviewRepository.save(review);
    }
}
