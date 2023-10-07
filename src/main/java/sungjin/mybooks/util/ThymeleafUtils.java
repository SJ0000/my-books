package sungjin.mybooks.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.util.HashMap;

public class ThymeleafUtils {

    private final HttpServletRequest request;

    public ThymeleafUtils(HttpServletRequest request) {
        this.request = request;
    }

    //Query String 중복 허용하지 않음
    public String getQueryStringChangedUri(String name, Object value) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(request.getRequestURI());
        request.getParameterMap().entrySet()
                .stream()
                .filter(e -> !e.getKey().equals(name))
                .forEach(e -> {
                    builder.queryParam(e.getKey(), e.getValue()[0]);
                });
        builder.queryParam(name, value);
        return builder.build().toUriString();
    }

    public int max(int a, int b){
        return Integer.max(a,b);
    }

    public int min(int a, int b){
        return Integer.min(a,b);
    }



}
