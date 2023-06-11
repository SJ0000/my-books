package sungjin.mybooks.config.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSession {

    private Long userId;
    private String name;

    @Builder
    public UserSession(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

}
