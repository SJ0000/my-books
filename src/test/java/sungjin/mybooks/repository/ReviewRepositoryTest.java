package sungjin.mybooks.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.Review;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("특정 사용자가 가진 책 리스트를 이름으로 검색하여 조회한다.")
    void findAllByBookTitleTest() throws Exception {
        // given
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);

        List<Book> books = MyBooksTestUtils.createBooks(3);
        bookRepository.saveAll(books);

        books.forEach((book) -> {
            Review userBook = MyBooksTestUtils.createReview(user, book, "content");
            reviewRepository.save(userBook);
        });

        // when
        Page<Review> result = reviewRepository.findAllByBookTitle(user.getId(), "test book", PageRequest.of(0, 10));

        // then
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 사용자가 가진 책 리스트를 최신순으로 조회한다.")
    void findRecentReviewsTest() throws Exception {
        // given
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);

        List<Book> books = MyBooksTestUtils.createBooks(20);
        bookRepository.saveAll(books);

        books.forEach(book -> {
            Review review = MyBooksTestUtils.createReview(user, book, "content");
            ReflectionTestUtils.setField(review, "createdAt", MyBooksTestUtils.randomDateTime());
            reviewRepository.save(review);
        });

        // when
        Page<Review> result = reviewRepository.findRecentReviews(user.getId(), PageRequest.of(0, 10));

        // then
        assertThat(result.getContent().size()).isEqualTo(10);
        assertThat(result.getContent()).isSortedAccordingTo((ub1, ub2) -> {
            return ub2.getCreatedAt().compareTo(ub1.getCreatedAt());
        });
    }
}