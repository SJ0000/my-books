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
    private String rawPassword;

    @Builder
    public Join(String email, String name, String rawPassword) {
        this.email = email;
        this.name = name;
        this.rawPassword = rawPassword;
    }
}
