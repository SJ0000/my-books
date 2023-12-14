package sungjin.mybooks.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import sungjin.mybooks.domain.common.domain.BaseTimeEntity;

import java.util.Objects;

@Entity
@Getter
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String password;

    @Builder
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public boolean isCorrectPassword(String password){
        return this.password.equals(password);
    }

}
