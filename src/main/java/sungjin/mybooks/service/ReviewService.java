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
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final UserService userService;

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

    @Transactional(readOnly = true)
    public Review findReview(Long id){
        return reviewRepository.findById(id)
                .orElseThrow( ()-> new RuntimeException("find review error. id = " + id));
    }

}
