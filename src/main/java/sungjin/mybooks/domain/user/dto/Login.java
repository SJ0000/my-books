package sungjin.mybooks.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Login {

    @Email
    @Size(max = 255)
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Size(min = 10, max = 20)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
