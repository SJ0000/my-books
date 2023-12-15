package sungjin.mybooks.domain.review.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.book.repository.BookRepository;
import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.environment.fixture.Fixtures;

import java.util.List;
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

    @Test
    @Transactional
    @DisplayName("native Query로 작성된 likes의 exists 테스트")
    void existsTest() {
        Like like = Fixtures.like().create();
        userRepository.saveAll(List.of(like.getUser(),like.getReview().getUser()));
        bookRepository.save(like.getReview().getBook());
        reviewRepository.save(like.getReview());
        likeRepository.save(like);

        boolean exists = likeRepository.exists(like.getUser().getId(), like.getReview().getId());
        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("named query 메서드 findByUserIdAndReviewIdTest 테스트")
    void findByUserIdAndReviewIdTest() {
        // given
        Like like = Fixtures.like().create();
        userRepository.saveAll(List.of(like.getUser(),like.getReview().getUser()));
        bookRepository.save(like.getReview().getBook());
        reviewRepository.save(like.getReview());
        likeRepository.save(like);

        // when
        Optional<Like> optionalLike = likeRepository.findByUserIdAndReviewId(like.getUser().getId(), like.getReview().getId());

        // then
        assertThat(optionalLike.isPresent()).isTrue();
        assertThat(optionalLike.get().getUser()).isEqualTo(like.getUser());
        assertThat(optionalLike.get().getReview()).isEqualTo(like.getReview());
    }
}