package sungjin.mybooks.domain.review.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.book.repository.BookRepository;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.environment.fixture.Fixtures;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("특정 사용자의 리뷰를 책 제목으로 검색하여 조회한다.")
    void findAllByBookTitleTest() throws Exception {
        // given
        User user = Fixtures.user().create();
        userRepository.save(user);

        String titleContains = "book";
        List<Book> books = Fixtures.book().createBooks(3,titleContains);
        bookRepository.saveAll(books);

        books.forEach((book) -> {
            System.out.println("title = " + book.getTitle());
            Review userBook = Fixtures.review().create(user,book);
            reviewRepository.save(userBook);
        });

        // when
        Page<Review> result = reviewRepository.findAllByBookTitle(user.getId(), titleContains, PageRequest.of(0, 10));

        // then
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 사용자의 리뷰를 최신순으로 조회한다.")
    void findRecentReviewsTest() throws Exception {
        // given
        User user = Fixtures.user().create();
        userRepository.save(user);

        List<Book> books = Fixtures.book().createBooks(20);
        bookRepository.saveAll(books);

        books.forEach(book -> {
            Review review = Fixtures.review().create(user, book);
            ReflectionTestUtils.setField(review, "createdAt", Fixtures.api().createDateTime());
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