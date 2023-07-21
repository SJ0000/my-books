package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.exception.Unauthorized;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.repository.UserBookRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserBookRepository userBookRepository;

    @Transactional
    public Long writeReview(Long userId, ReviewCreate reviewCreate){
        // Review 생성
        Long userBookId = reviewCreate.getUserBookId();

        UserBook userBook = userBookRepository.findById(userBookId)
               .orElseThrow(() -> new NotFound(UserBook.class, "id", userBookId));

        if(!userBook.isOwner(userId))
            throw new Unauthorized();

        Review review = Review.builder()
                .userBook(userBook)
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
