package sungjin.mybooks.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Like;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    TestEntityManager em;

    @BeforeEach
    void beforeEach(){
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);
        Book book = MyBooksTestUtils.createBook();
        bookRepository.save(book);
        Review review = MyBooksTestUtils.createReview(user, book, "content");
        reviewRepository.save(review);
        em.flush();
        em.clear();
    }


    @Test
    @DisplayName("native Query로 작성된 likes의 exists 테스트")
    void existsTest() {
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);
        Book book = MyBooksTestUtils.createBook();
        bookRepository.save(book);
        Review review = MyBooksTestUtils.createReview(user, book, "content");
        reviewRepository.save(review);

        likeRepository.save(Like.builder()
                .user(user)
                .review(review)
                .build());

        boolean exists = likeRepository.exists(user.getId(), review.getId());
        assertThat(exists).isTrue();
    }
}