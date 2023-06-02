package sungjin.mybooks.util;

import jakarta.servlet.http.Cookie;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    public static Optional<Cookie> findCookie(Cookie[] cookies, String name){
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(name))
                .findAny();
    }
}
