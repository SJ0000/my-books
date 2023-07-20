package sungjin.mybooks.dto.response;

import lombok.*;
import sungjin.mybooks.domain.Review;

@Getter
@Setter
@ToString
public class ReviewResponse {

    private Long id;
    private String content;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
    }
}
