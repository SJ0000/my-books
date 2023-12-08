package sungjin.mybooks.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;

@RedisHash
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Long userId;

    @TimeToLive
    private Long timeToLiveSeconds;

    private LocalDateTime expireDate;

    public Session(String id, Long userId, Long timeToLiveSeconds) {
        this.id = id;
        this.userId = userId;
        this.timeToLiveSeconds = timeToLiveSeconds;
        this.expireDate = LocalDateTime.now().plusSeconds(timeToLiveSeconds);
    }
}
