package sungjin.mybooks.global.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

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

    public String getPreviewOfContent(String content){
        if(!StringUtils.hasText(content))
            return "내용 없음";

        if(content.length() <= 10)
            return content;

        return content.substring(0,10) + " ...";
    }

}
