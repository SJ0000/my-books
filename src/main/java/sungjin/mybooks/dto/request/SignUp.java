package sungjin.mybooks.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUp {

    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    @Builder
    public SignUp(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
