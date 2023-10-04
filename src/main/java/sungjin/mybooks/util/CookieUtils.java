package sungjin.mybooks.util;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.boot.web.server.Cookie.SameSite.*;

@Component
public class CookieUtils {

    @Value("${app.domain}")
    private static String domain;

    public static Optional<String> getCookieValue(Cookie[] cookies, String name){
        if(cookies == null)
            return Optional.empty();

        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(name))
                .map(Cookie::getValue)
                .findAny();
    }

    public static boolean hasCookie(Cookie[] cookies, String name){
        if(cookies == null)
            return false;

        return Arrays.stream(cookies)
                .anyMatch(c -> c.getName().equals(name));
    }

    public static ResponseCookie createCookie(String name, String value){
        return ResponseCookie.from(name, value)
                .domain(domain)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite(STRICT.toString())
                .build();
    }

    public static ResponseCookie createCookieForExpire(String name){
        return ResponseCookie.from(name, "")
                .domain(domain)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite(STRICT.toString())
                .build();
    }

}
