package sungjin.mybooks.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Session(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
