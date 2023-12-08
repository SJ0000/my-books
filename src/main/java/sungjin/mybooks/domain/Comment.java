package sungjin.mybooks.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    private String content;

    @Builder
    public Comment(User user, Review review, String content) {
        this.user = user;
        this.review = review;
        this.content = content;
    }

    public boolean isOwner(Long userId){
        return user.getId().equals(userId);
    }
}
