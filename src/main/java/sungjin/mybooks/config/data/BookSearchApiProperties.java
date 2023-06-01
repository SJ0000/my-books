package sungjin.mybooks.config.data;



import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties("book-search-api.kakao")
public class BookSearchApiProperties {
    private String host;
    private String uri;
    private String authorization;
}
