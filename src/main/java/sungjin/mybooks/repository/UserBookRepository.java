package sungjin.mybooks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sungjin.mybooks.domain.UserBook;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    @Query("select ub from UserBook ub where ub.user.id = :userId and ub.book.title like %:title%")
    List<UserBook> findAllByBookTitle(Long userId, String title);

}
