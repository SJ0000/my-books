package sungjin.mybooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.*;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.service.AuthService;
import sungjin.mybooks.util.CookieNames;

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
    ReviewRepository reviewRepository;
    @Autowired
    AuthService authService;
    @Autowired
    ObjectMapper om;

    @Test
    @DisplayName("특정 리뷰 조회")
    void getReviewTest() throws Exception {
        // given
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);
        Book book = MyBooksTestUtils.createBook();
        bookRepository.save(book);
        Review review = MyBooksTestUtils.createReview(user,book,"content");
        reviewRepository.save(review);

        Long reviewId = review.getId();

        // expected
        mockMvc.perform(get("/review/{id}", reviewId))
                .andExpect(jsonPath("$.id").value(reviewId))
                .andExpect(jsonPath("$.content").value(review.getContent()));
    }

    @Test
    @DisplayName("리뷰 작성")
    void createReviewTest() throws Exception {
        // given
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);

        Book book = MyBooksTestUtils.createBook();
        bookRepository.save(book);

        Session session = authService.createSession(user);

        ReviewCreate reviewCreate = ReviewCreate.builder()
                .bookId(book.getId())
                .content("test review")
                .build();

        // expected
        mockMvc.perform(post("/review")
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getAccessToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(reviewCreate)))
                .andExpect(status().isCreated());

    }
}