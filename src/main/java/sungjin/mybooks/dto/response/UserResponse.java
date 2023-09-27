package sungjin.mybooks.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import sungjin.mybooks.domain.User;

@Getter
public class UserResponse {
    private final Long id;
    private final String email;
    private final String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
