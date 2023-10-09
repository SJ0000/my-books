package sungjin.mybooks.config.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class UserSession {

    private final Long userId;
    private final String accessToken;

    @Builder
    public UserSession(Long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

}
