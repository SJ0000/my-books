package sungjin.mybooks.domain.common.aop;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.book.repository.BookRepository;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.review.repository.ReviewRepository;
import sungjin.mybooks.domain.review.service.ReviewService;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.environment.MyBooksTestUtils;
import sungjin.mybooks.environment.fixture.Fixtures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class DomainAuthorizeAspectTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("적절한 사용자가 접근한 경우 ReviewService에서 수정이 인가되어야 한다.")
    @Transactional
    void authorizeSuccess() throws Exception {
        // given
        Review review = Fixtures.review().create();
        MyBooksTestUtils.saveCascade(em, review);

        String newContents = Fixtures.api().createString(200);
        // when
        reviewService.editReview(review.getId(), review.getUser().getId(), newContents);

        // then
        assertThat(review.getContent()).isEqualTo(newContents);
    }

    @Test
    @DisplayName("권한이 없는 사용자가 접근한 경우 ReviewService에서 수정이 인가되지 않아야 한다.")
    @Transactional
    void authorizeFail() throws Exception {
        // given
        Review review = Fixtures.review().create();
        MyBooksTestUtils.saveCascade(em, review);

        // expected
        assertThatThrownBy(() -> reviewService.editReview(review.getId(), review.getUser().getId() + 1, "1"))
                .isInstanceOf(RuntimeException.class);
    }
}