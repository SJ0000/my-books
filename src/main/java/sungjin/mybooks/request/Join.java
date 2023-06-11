package sungjin.mybooks.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Join {

    private String email;
    private String name;
    private String password;

    @Builder
    public Join(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
