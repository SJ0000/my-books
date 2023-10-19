package sungjin.mybooks.search;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sungjin.mybooks.config.data.BookSearchApiProperties;

import static org.junit.jupiter.api.Assertions.*;


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
    void test(){
        BookSearchResult dog = searchApi.search("dog",1,10);
        BookSearchResult.Meta meta = dog.getMeta();
        System.out.println(meta);
        for (BookSearchResult.Document document : dog.getDocuments()) {
            System.out.println(document);
        }
    }

    @Test
    @DisplayName("ISBN으로 도서를 검색한다")
    void searchByIsbnTest(){
        String isbn = "9788960777330";
        BookSearchResult result = searchApi.searchByIsbn(isbn);
        Assertions.assertThat(result.getDocuments().size()).isEqualTo(1);
        Assertions.assertThat(result.getDocuments().get(0).getTitle()).isEqualTo("자바 ORM 표준 JPA 프로그래밍");
    }
}