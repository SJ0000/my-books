package sungjin.mybooks.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.repository.UserBookRepository;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller Test는 통합테스트로 작성
 */

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserBookRepository userBookRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("특정 리뷰 조회")
    void getReviewTest() throws Exception {
        // given
        User user = User.builder()
                .name("user")
                .email("abcd@efgh.com")
                .password("1234")
                .build();
        userRepository.save(user);

        Book book = Book.builder()
                .title("book 1")
                .isbn("123456789012")
                .build();
        bookRepository.save(book);

        UserBook userbook = UserBook.builder()
                .user(user)
                .book(book)
                .build();
        userBookRepository.save(userbook);

        Review review = Review.builder()
                .userBook(userbook)
                .content("Review 01")
                .build();
        reviewRepository.save(review);

        Long reviewId = review.getId();

        // expected
        mockMvc.perform(get("/review/{id}",reviewId))
                .andExpect(jsonPath("$.id").value(reviewId))
                .andExpect(jsonPath("$.content").value(review.getContent()));

    }
}