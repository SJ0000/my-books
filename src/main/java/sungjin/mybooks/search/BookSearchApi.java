package sungjin.mybooks.search;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

public class BookSearchApi {

    private final WebClient webClient;

    public BookSearchApi(BookSearchApiProperties properties) {
        webClient = WebClient.builder()
                .baseUrl(properties.getHost() + properties.getUri())
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.getAuthorization())
                .build();
    }

    public BookSearchResult search(String query) {
        ResponseEntity<BookSearchResult> response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .toEntity(BookSearchResult.class)
                .block();

        return response.getBody();
    }
}
