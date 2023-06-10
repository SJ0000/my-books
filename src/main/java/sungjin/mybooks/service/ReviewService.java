package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.request.ReviewCreate;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final UserService userService;

    public Review writeReview(Long userId, ReviewCreate reviewCreate){
        // Review 생성
        User user = userService.findUser(userId);
        Book book = bookService.findBook(reviewCreate.getBookId());
        Review review = Review.builder()
                .user(user)
                .book(book)
                .content(reviewCreate.getContent())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Review findReview(Long id){
        return reviewRepository.findById(id)
                .orElseThrow( ()-> new RuntimeException("find review error. id = " + id));
    }

}
