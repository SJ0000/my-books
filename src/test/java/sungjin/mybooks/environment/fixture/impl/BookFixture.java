package sungjin.mybooks.environment.fixture.impl;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.environment.fixture.EntityFixture;

import java.util.List;

import static net.jqwik.api.Arbitraries.strings;

public class BookFixture extends EntityFixture<Book> {


    @Override
    public Book create() {
        return getBookBuilder().sample();
    }

    public List<Book> createBooks(int count) {
        return getBookBuilder().sampleList(count);
    }

    public List<Book> createBooks(int count, String titleContains) {
        List<Book> books = getBookBuilder().sampleList(count);
        books.forEach(book -> {
            ReflectionTestUtils.setField(book, "title", book.getTitle() + titleContains);
        });
        return books;
    }

    private ArbitraryBuilder<Book> getBookBuilder() {
        return fixtureMonkey.giveMeBuilder(Book.class)
                .set("isbn", strings().numeric().ofLength(13))
                .set("title", strings().ascii().ofMaxLength(255));
    }
}
