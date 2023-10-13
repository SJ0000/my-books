package sungjin.mybooks.dto.response;

import lombok.*;
import sungjin.mybooks.domain.Review;

import java.time.LocalDateTime;

@Getter
@ToString
public class ReviewResponse {

    private final Long id;
    private final String bookTitle;
    private final String content;
    private final String thumbnail;
    private final LocalDateTime modifedAt;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.bookTitle = review.getBook().getTitle();
        this.content = review.getContent();
        this.thumbnail = review.getBook().getThumbnail();
        this.modifedAt = review.getModifiedAt();
    }
}
