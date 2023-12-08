package sungjin.mybooks.infra.search;

import io.micrometer.core.annotation.Counted;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


public class BookSearchApi {

    private final WebClient webClient;

    public BookSearchApi(BookSearchApiProperties properties) {
        this.webClient = WebClient.builder()
                .baseUrl(properties.getHost() + properties.getUri())
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.getAuthorization())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Counted("api.search-book")
    public BookSearchResult request(BookSearchParameters parameters){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParams(parameters.createMultiValueMap())
                        .build())
                .retrieve()
                .bodyToMono(BookSearchResult.class)
                .block();
    }
}
