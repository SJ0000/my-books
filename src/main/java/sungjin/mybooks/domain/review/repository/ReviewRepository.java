package sungjin.mybooks.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sungjin.mybooks.domain.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select r from Review r join fetch r.book where r.user.id = :userId and r.book.title like %:title%",
            countQuery = "select count(r) from Review r where r.user.id = :userId and r.book.title like %:title%")
    Page<Review> findAllByBookTitle(Long userId, String title, Pageable pageable);

    @Query(value = "select r from Review r join fetch r.book where r.user.id = :userId order by r.createdAt DESC",
            countQuery = "select count(r) from Review r where r.user.id = :userId")
    Page<Review> findRecentReviews(Long userId, Pageable pageable);

}
