package sungjin.mybooks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.UserBook;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    @Query(value = "select ub from UserBook ub join fetch ub.book where ub.user.id = :userId and ub.book.title like %:title%",
            countQuery = "select count(ub) from UserBook ub where ub.user.id = :userId and ub.book.title like %:title%")
    Page<UserBook> findAllByBookTitle(Long userId, String title, Pageable pageable);

    @Query(value = "select ub from UserBook ub join fetch ub.book where ub.user.id = :userId order by ub.createdAt DESC",
            countQuery = "select count(ub) from UserBook ub where ub.user.id = :userId")
    Page<UserBook> findRecentUserBooks(Long userId, Pageable pageable);

}
