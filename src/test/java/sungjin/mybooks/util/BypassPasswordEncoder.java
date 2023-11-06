package sungjin.mybooks.util;

import sungjin.mybooks.security.PasswordEncoder;

public class BypassPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String text) {
        return text;
    }
}
