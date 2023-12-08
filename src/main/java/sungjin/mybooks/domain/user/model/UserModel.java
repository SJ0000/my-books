package sungjin.mybooks.domain.user.model;

import lombok.Getter;
import sungjin.mybooks.domain.user.domain.User;

@Getter
public class UserModel {
    private final Long id;
    private final String email;
    private final String name;

    public UserModel(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
