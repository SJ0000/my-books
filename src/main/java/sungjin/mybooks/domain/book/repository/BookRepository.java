package sungjin.mybooks.domain.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sungjin.mybooks.domain.book.domain.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
}
