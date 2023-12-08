package sungjin.mybooks.infra.search;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sungjin.mybooks.infra.search.BookSearchApiProperties;
import sungjin.mybooks.infra.search.BookSearchApi;
import sungjin.mybooks.infra.search.BookSearchParameters;
import sungjin.mybooks.infra.search.BookSearchResult;


class BookSearchApiTest {

    private final BookSearchApi searchApi;

    public BookSearchApiTest() {
        BookSearchApiProperties properties = new BookSearchApiProperties();
        properties.setHost("dapi.kakao.com");
        properties.setUri("/v3/search/book");
        properties.setAuthorization("KakaoAK e6655a4deee04490bcdbbe7faab5a3fb");
        this.searchApi = new BookSearchApi(properties);
    }

    @Test
    @DisplayName("ISBN으로 도서를 검색한다")
    void requestTest(){
        BookSearchParameters param = BookSearchParameters.isbn("9788960777330");
        BookSearchResult result = searchApi.request(param);
        Assertions.assertThat(result.getDocuments().size()).isEqualTo(1);
        Assertions.assertThat(result.getDocuments().get(0).getTitle()).isEqualTo("자바 ORM 표준 JPA 프로그래밍");
    }
}