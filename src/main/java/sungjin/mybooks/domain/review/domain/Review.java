package sungjin.mybooks.domain.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.global.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Lob
    private String content;

    @Builder
    public Review(User user, Book book, String content) {
        this.user = user;
        this.book = book;
        this.content = content;
    }

    public boolean isOwner(Long userId){
        return user.getId().equals(userId);
    }

    public void editContent(String content){
        this.content = content;
    }

}
