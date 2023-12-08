package sungjin.mybooks.domain.review.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.environment.MyBooksTestUtils;
import sungjin.mybooks.domain.user.domain.User;

import static org.assertj.core.api.Assertions.*;

class ReviewTest {

    @Test
    @DisplayName("리뷰 소유자 확인")
    void isOwnerTest(){
        // given
        Long userId = 1L;
        User user = MyBooksTestUtils.createUser();
        ReflectionTestUtils.setField(user,"id",userId);
        Review review = MyBooksTestUtils.createReview(user, null, "");

        // expected
        assertThat(review.isOwner(userId)).isTrue();
        assertThat(review.isOwner(userId+1)).isFalse();
    }

    @Test
    @DisplayName("리뷰 수정")
    void editContentTest(){
        // given
        Review review = MyBooksTestUtils.createReview(null, null, "origin Review");
        String newReview = "new review";

        // when
        review.editContent(newReview);

        // then
        assertThat(review.getContent()).isEqualTo(newReview);
    }
}