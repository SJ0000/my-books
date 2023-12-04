package sungjin.mybooks.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "LIKES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    @Id
    private Long id;

    @ManyToOne
    private Review review;

    @ManyToOne
    private User user;

    @Builder
    public Like(Review review, User user) {
        this.review = review;
        this.user = user;
    }
}
