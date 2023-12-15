package sungjin.mybooks.domain.review.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sungjin.mybooks.environment.fixture.Fixtures;

import static org.assertj.core.api.Assertions.assertThat;
import static sungjin.mybooks.environment.MyBooksTestUtils.createRandomString;

class ReviewTest {

    @Test
    @DisplayName("리뷰 수정")
    void editContentTest(){
        // given
        Review review = Fixtures.review().create(null, null);
        String newReview = createRandomString(250);

        // when
        review.editContent(newReview);

        // then
        assertThat(review.getContent()).isEqualTo(newReview);
    }
}