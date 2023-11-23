package sungjin.mybooks.search;

import io.micrometer.core.annotation.Counted;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import sungjin.mybooks.config.data.BookSearchApiProperties;


public class BookSearchApi {

    private final WebClient webClient;

    public BookSearchApi(BookSearchApiProperties properties) {
        this.webClient = WebClient.builder()
                .baseUrl(properties.getHost() + properties.getUri())
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.getAuthorization())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public BookSearchResult search(String query, int page, int size) {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("query",query);
        params.add("page",String.valueOf(page));
        params.add("size",String.valueOf(page));

        return apiSearch(params);
    }

    public BookSearchResult searchByIsbn(String isbn) {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("query",isbn);
        params.add("target","isbn");

        return apiSearch(params);
    }


    private BookSearchResult apiSearch(MultiValueMap<String,String> queryParams){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParams(queryParams)
                        .build())
                .retrieve()
                .bodyToMono(BookSearchResult.class)
                .block();
    }


}
