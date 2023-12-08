package sungjin.mybooks.domain.user.encrypt;

public interface PasswordEncoder {
    String encode(String text);
}
