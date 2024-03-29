package sungjin.mybooks.domain.review.model;

import lombok.Getter;
import lombok.ToString;
import sungjin.mybooks.domain.review.domain.Review;

import java.time.LocalDateTime;

@Getter
@ToString
public class ReviewModel {

    private final Long id;
    private final String bookTitle;
    private final String content;
    private final String thumbnail;
    private final LocalDateTime modifedAt;

    public ReviewModel(Review review) {
        this.id = review.getId();
        this.bookTitle = review.getBook().getTitle();
        this.content = review.getContent();
        this.thumbnail = review.getBook().getThumbnail();
        this.modifedAt = review.getModifiedAt();
    }
}
