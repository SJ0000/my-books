package sungjin.mybooks.search;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import sungjin.mybooks.config.data.BookSearchApiProperties;

public class BookSearchApi {

    private final WebClient webClient;

    public BookSearchApi(BookSearchApiProperties properties) {
        webClient = WebClient.builder()
                .baseUrl(properties.getHost() + properties.getUri())
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.getAuthorization())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public BookSearchResult search(String query) {
        BookSearchResult result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(BookSearchResult.class)
                .block();

        return result;
    }
}
