package sungjin.mybooks.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sungjin.mybooks.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
