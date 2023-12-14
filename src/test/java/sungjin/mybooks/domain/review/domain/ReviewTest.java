package sungjin.mybooks.domain.review.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sungjin.mybooks.environment.MyBooksTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewTest {

    @Test
    @DisplayName("리뷰 수정")
    void editContentTest(){
        // given
        Review review = MyBooksTestUtils.createReview(null, null);
        String newReview = "new review";

        // when
        review.editContent(newReview);

        // then
        assertThat(review.getContent()).isEqualTo(newReview);
    }
}