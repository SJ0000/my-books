package sungjin.mybooks.config.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSession {

    private final String sessionId;
    private final Long userId;

    @Builder
    public UserSession(String sessionId, Long userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

}
