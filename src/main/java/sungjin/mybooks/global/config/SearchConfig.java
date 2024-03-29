package sungjin.mybooks.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sungjin.mybooks.infra.search.BookSearchApi;
import sungjin.mybooks.infra.search.BookSearchApiProperties;

@Configuration
@EnableConfigurationProperties(BookSearchApiProperties.class)
public class SearchConfig {

    private final BookSearchApiProperties properties;

    public SearchConfig(BookSearchApiProperties properties) {
        this.properties = properties;
    }

    @Bean
    public BookSearchApi bookSearchApi(){
        return new BookSearchApi(properties);
    }

}
