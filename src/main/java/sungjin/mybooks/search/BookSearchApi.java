package sungjin.mybooks.search;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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

    public BookSearchResult search(String query, int page,int size) {
        BookSearchResult result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToMono(BookSearchResult.class)
                .block();

        return result;
    }


}
