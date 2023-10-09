package sungjin.mybooks.search;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookSearchApiTest {

    @Autowired
    BookSearchApi searchApi;

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