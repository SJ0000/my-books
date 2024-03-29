package sungjin.mybooks.domain.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.book.repository.BookRepository;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.review.repository.CommentRepository;
import sungjin.mybooks.domain.review.repository.LikeRepository;
import sungjin.mybooks.domain.review.repository.ReviewRepository;
import sungjin.mybooks.domain.user.domain.Session;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.domain.user.service.AuthService;
import sungjin.mybooks.environment.MyBooksTestUtils;
import sungjin.mybooks.environment.fixture.Fixtures;
import sungjin.mybooks.global.util.CookieNames;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
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
    ReviewRepository reviewRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    AuthService authService;
    @Autowired
    ObjectMapper om;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("GET /reviews 호출시 사용자의 리뷰를 Model에 전달한다.")
    void userReviews() throws Exception {
        // given
        User user = Fixtures.user().create();
        List<Book> books = Fixtures.book().createBooks(3);
        List<Review> reviews = books.stream()
                .map(book -> Fixtures.review().create(user, book)).toList();

        reviews.forEach(review -> MyBooksTestUtils.saveCascade(em, review));

        Session session = authService.createSession(user.getId());
        // expected
        mockMvc.perform(get("/reviews")
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(model().attributeExists("reviews", "page"))
                .andExpect(view().name("review-list"));
    }

    @Test
    @DisplayName("GET /reviews 에서 query 검색시 검색 조건에 맞는 사용자의 리뷰를 Model에 전달한다.")
    void userReviewsBookTitle() throws Exception {
        // given
        String query = "book";
        User user = Fixtures.user().create();
        List<Book> books = Fixtures.book().createBooks(3,query);
        List<Review> reviews = books.stream()
                .map(book -> Fixtures.review().create(user, book)).toList();

        reviews.forEach(review -> MyBooksTestUtils.saveCascade(em, review));

        Session session = authService.createSession(user.getId());
        // expected
        mockMvc.perform(get("/reviews")
                        .queryParam("query", query)
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(model().attributeExists("reviews", "page"))
                .andExpect(model().attribute("reviews", hasItem(hasProperty("bookTitle", containsString(query)))))
                .andExpect(view().name("review-list"));
    }

    @Test
    @DisplayName("GET /reviews/{id} 호출시 Review Id와 일치하는 리뷰를 Model에 전달한다.")
    void getReview() throws Exception {
        // given
        Review review = Fixtures.review().create();
        MyBooksTestUtils.saveCascade(em,review);

        // expected
        mockMvc.perform(get("/reviews/{id}", review.getId()))
                .andExpect(model().attribute("review", hasProperty("id", equalTo(review.getId()))))
                .andExpect(view().name("review-read"));
    }

    @Test
    @DisplayName("GET /review-create 호출시 book을 Model에 담아 전달해야 한다.")
    void reviewCreateForm() throws Exception {
        // given
        User user = Fixtures.user().create();
        em.persist(user);

        Session session = authService.createSession(user.getId());
        String isbn = "9791161571577"; // 실제 등록된 책의 isbn

        // expected
        mockMvc.perform(get("/review-create")
                        .queryParam("isbn", isbn)
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(model().attribute("book", hasProperty("isbn", equalTo(isbn))))
                .andExpect(view().name("review-create"));
    }

    @Test
    @DisplayName("POST /reviews 호출시 리뷰를 생성 후 생성된 리뷰 페이지로 redirect 한다")
    void createReviewTest() throws Exception {
        // given
        User user = Fixtures.user().create();
        Book book = Fixtures.book().create();
        em.persist(user);
        em.persist(book);

        Session session = authService.createSession(user.getId());

        String content = Fixtures.api().createString(200);
        // when
        ResultActions result = mockMvc.perform(post("/reviews")
                .param("bookId", book.getId().toString())
                .contentType(APPLICATION_FORM_URLENCODED)
                .content("content=" + content)
                .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())));

        // then
        Review review = reviewRepository.findAll().get(0);
        assertThat(review.getContent()).isEqualTo(content);
        assertThat(review.getBook()).isEqualTo(book);
        assertThat(review.getUser()).isEqualTo(user);

        result.andExpect(redirectedUrl("/reviews/" + review.getId()));
    }

    @Test
    @DisplayName("GET /review/edit 호출시 id가 일치하는 review를 model에 전달한다.")
    void editReviewForm() throws Exception {
        // given
        Review review = Fixtures.review().create();
        MyBooksTestUtils.saveCascade(em,review);

        Session session = authService.createSession(review.getUser().getId());

        // expected
        mockMvc.perform(get("/review/edit")
                        .param("id", review.getId().toString())
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(model().attributeExists("review"))
                .andExpect(view().name("review-edit"));
    }

    @Test
    @DisplayName("POST /review/edit 호출시 리뷰를 수정 후 수정된 리뷰 페이지로 redirect 한다")
    void editReview() throws Exception {
        // given
        Review review = Fixtures.review().create();
        MyBooksTestUtils.saveCascade(em,review);

        Session session = authService.createSession(review.getUser().getId());
        String content = Fixtures.api().createString(200);

        // when
        ResultActions result = mockMvc.perform(post("/review/edit")
                .param("id", review.getId().toString())
                .contentType(APPLICATION_FORM_URLENCODED)
                .content("content="+content)
                .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())));

        // then
        Review findReview = reviewRepository.findById(review.getId()).get();

        assertThat(findReview.getId()).isEqualTo(review.getId());
        assertThat(findReview.getContent()).isEqualTo(content);

        result.andExpect(redirectedUrl("/reviews/" + review.getId()));
    }

    @Test
    @DisplayName("DELETE /review/{id} 호출시 리뷰를 삭제 후 204 NO CONTENT 로 응답")
    void deleteReview() throws Exception {
        // given
        Review review = Fixtures.review().create();
        MyBooksTestUtils.saveCascade(em,review);

        Session session = authService.createSession(review.getUser().getId());

        // expected
        mockMvc.perform(delete("/reviews/{id}", review.getId())
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /reviews/{id}/like 호출시 리뷰에 like를 추가 후 201 CREATED로 응답")
    void likeReview() throws Exception {
        // given
        Review review = Fixtures.review().create();
        MyBooksTestUtils.saveCascade(em,review);

        Session session = authService.createSession(review.getUser().getId());

        // expected
        mockMvc.perform(post("/reviews/{id}/like", review.getId())
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(status().isCreated());

        Like like = likeRepository.findAll().get(0);
        assertThat(like.getUser()).isEqualTo(review.getUser());
        assertThat(like.getReview()).isEqualTo(review);
    }

    @Test
    @DisplayName("DELETE /reviews/{id}/like 호출시 like 삭제 후 204 NO_CONTENT 응답")
    void cancelLikeReview() throws Exception {
        // given
        Like like = Fixtures.like().create();
        MyBooksTestUtils.saveCascade(em,like);

        Session session = authService.createSession(like.getUser().getId());

        // expected
        mockMvc.perform(delete("/reviews/{id}/like", like.getReview().getId())
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(status().isNoContent());

        Optional<Like> optionalLike = likeRepository.findByUserIdAndReviewId(like.getReview().getUser().getId(), like.getReview().getId());
        assertThat(optionalLike.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("POST /reviews/{id}/comment 호출시 리뷰에 comment 추가 후 /reviews/{id} redirect")
    void addComment() throws Exception {
        // given
        Review review = Fixtures.review().create();
        MyBooksTestUtils.saveCascade(em,review);

        User commentWriter = Fixtures.user().create();
        em.persist(commentWriter);
        String comment = Fixtures.api().createString(200);
        Session session = authService.createSession(commentWriter.getId());

        // expected
        mockMvc.perform(post("/reviews/{id}/comment", review.getId())
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId()))
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .content("comment=" + comment))
                .andExpect(redirectedUrl("/reviews/" + review.getId()));

        Comment findComment = commentRepository.findAll().get(0);
        System.out.println(findComment.getUser().getId() + " " + findComment.getReview().getId() + " " + findComment.getContent());
        assertThat(findComment.getUser()).isEqualTo(commentWriter);
        assertThat(findComment.getContent()).isEqualTo(comment);
    }

    @Test
    @DisplayName("DELETE /comments/{id} 호출시 리뷰 삭제 후 204 NO CONTENT 응답")
    void removeComment() throws Exception {
        // given
        Comment comment = Fixtures.comment().create();
        MyBooksTestUtils.saveCascade(em, comment);

        Session session = authService.createSession(comment.getUser().getId());
        // expected
        mockMvc.perform(delete("/comments/{id}", comment.getId())
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(status().isNoContent());

        assertThat(commentRepository.findAll()).isEmpty();
    }
}

