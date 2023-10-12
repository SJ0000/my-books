package sungjin.mybooks.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.ReviewRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @MockBean
    ReviewRepository reviewRepository;


    @Test
    @DisplayName("리뷰 조회 실패시 NotFound Exception 발생")
    void findReviewNotFound() throws Exception {
        // given
        BDDMockito.given(reviewRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // expected
        assertThatThrownBy(() -> reviewService.findReview(1L))
                .isInstanceOf(NotFound.class);
    }



}