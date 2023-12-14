package sungjin.mybooks.domain.review.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.book.repository.BookRepository;
import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.environment.MyBooksTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LikeRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LikeRepository likeRepository;

    @BeforeEach
    void beforeEach(){
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);
        Book book = MyBooksTestUtils.createBook();
        bookRepository.save(book);
        Review review = MyBooksTestUtils.createReview(user, book);
        reviewRepository.save(review);
    }


    @Test
    @DisplayName("native Query로 작성된 likes의 exists 테스트")
    void existsTest() {
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);
        Book book = MyBooksTestUtils.createBook();
        bookRepository.save(book);
        Review review = MyBooksTestUtils.createReview(user, book);
        reviewRepository.save(review);

        likeRepository.save(Like.builder()
                .user(user)
                .review(review)
                .build());

        boolean exists = likeRepository.exists(user.getId(), review.getId());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("named query 메서드 findByUserIdAndReviewIdTest 테스트")
    void findByUserIdAndReviewIdTest() {
        // given
        User user = userRepository.findAll().get(0);
        Review review = reviewRepository.findAll().get(0);
        likeRepository.save(Like.builder()
                .user(user)
                .review(review)
                .build());

        // when
        Optional<Like> optionalLike = likeRepository.findByUserIdAndReviewId(user.getId(), review.getId());

        // then
        assertThat(optionalLike.isPresent()).isTrue();
        assertThat(optionalLike.get().getUser()).isEqualTo(user);
        assertThat(optionalLike.get().getReview()).isEqualTo(review);
    }
}