package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;
import sungjin.mybooks.dto.request.ReviewCreate;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.exception.Unauthorized;
import sungjin.mybooks.repository.UserBookRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @MockBean
    UserBookRepository userBookRepository;

    @Test
    @DisplayName("리뷰 작성시 userbook이 없을 경우 NotFound Exception 발생")
    void writeReviewNotFound() throws Exception {
        // given
        BDDMockito.given(userBookRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        ReviewCreate reviewCreate = ReviewCreate.builder()
                .userBookId(1L)
                .content("content")
                .build();

        // expected
        assertThatThrownBy(() -> reviewService.writeReview(1L, reviewCreate))
                .isInstanceOf(NotFound.class);
    }

    @Test
    @DisplayName("리뷰 작성자와 userbook의 소유주가 일치하지 않을 경우 Unauthorized Exception 발생")
    void writeReviewUnauthorized() throws Exception {
        // given
        long userId = 1L;
        User mockUser = MyBooksTestUtils.createUser();
        ReflectionTestUtils.setField(mockUser,"id",userId);
        UserBook mockUserBook = UserBook.builder()
                .user(mockUser)
                .build();

        BDDMockito.given(userBookRepository.findById(anyLong()))
                .willReturn(Optional.of(mockUserBook));

        ReviewCreate reviewCreate = ReviewCreate.builder()
                .userBookId(1L)
                .content("content")
                .build();

        // expected
        assertThatThrownBy(() -> reviewService.writeReview(userId+1, reviewCreate))
                .isInstanceOf(Unauthorized.class);
    }

    @Test
    @DisplayName("리뷰 조회 실패시 NotFound Exception 발생")
    void findReviewNotFound() throws Exception {
        // given
        BDDMockito.given(userBookRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // expected
        assertThatThrownBy(() -> reviewService.findReview(1L))
                .isInstanceOf(NotFound.class);
    }
}