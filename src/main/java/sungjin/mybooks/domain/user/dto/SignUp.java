package sungjin.mybooks.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUp {

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(max = 100)
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
