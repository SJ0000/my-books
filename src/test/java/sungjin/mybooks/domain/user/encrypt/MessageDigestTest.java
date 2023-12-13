package sungjin.mybooks.domain.user.encrypt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestTest {

    @Test()
    @DisplayName("MessageDigest 동작 테스트")
    void test() throws NoSuchAlgorithmException {
        String rawPassword = "dog";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(rawPassword.getBytes(StandardCharsets.UTF_8));

        // String s = new String(digest.digest(),StandardCharsets.UTF_8);
        byte[] hashedBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x",b));
        }

        System.out.println(sb.toString());
    }

}
