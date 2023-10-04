package sungjin.mybooks.search;

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
        BookSearchResult dog = searchApi.search("dog",1);
        BookSearchResult.Meta meta = dog.getMeta();
        System.out.println(meta);
        for (BookSearchResult.Document document : dog.getDocuments()) {
            System.out.println(document);
        }
    }
}