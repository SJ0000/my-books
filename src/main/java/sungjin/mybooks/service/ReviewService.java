package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.dto.request.ReviewCreate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final UserService userService;

    @Transactional
    public Long writeReview(Long userId, ReviewCreate reviewCreate){
        // Review 생성
        User user = userService.findUser(userId);
        Book book = bookService.findBook(reviewCreate.getUserBookId());
        Review review = Review.builder()
                .user(user)
                .book(book)
                .content(reviewCreate.getContent())
                .build();

        reviewRepository.save(review);
        return review.getId();
    }

    public Review findReview(Long id){
        return reviewRepository.findById(id)
                .orElseThrow( ()-> new NotFound(Review.class, "id", id));
    }

    public void editReview(Long id, String content){
        Review review = findReview(id);
        review.editContent(content);
    }

}
