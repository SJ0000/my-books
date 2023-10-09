package sungjin.mybooks.dto.response;

import lombok.*;
import sungjin.mybooks.domain.Review;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReviewResponse {

    private Long id;
    private String bookTitle;
    private String content;
    private String thumbnail;
    private LocalDateTime modifedAt;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.bookTitle = review.getBook().getTitle();
        this.content = review.getContent();
        this.thumbnail = review.getBook().getThumbnail();
        this.modifedAt = review.getModifiedAt();
    }
}
