package sungjin.mybooks.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUp {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @Size(min = 10, max = 20)
    @NotBlank
    private String password;

    @Builder
    public SignUp(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
