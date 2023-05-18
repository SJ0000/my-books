package sungjin.mybooks.search;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BookSearchApiProperties.class)
public class SearchConfiguration {

    private final BookSearchApiProperties properties;

    public SearchConfiguration(BookSearchApiProperties properties) {
        this.properties = properties;
    }

    @Bean
    public BookSearchApi bookSearchApi(){
        return new BookSearchApi(properties);
    }

}
