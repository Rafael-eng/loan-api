package br.com.elotech.repository;

import br.com.elotech.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT l FROM Book l WHERE l.category IN (" +
            "SELECT DISTINCT l2.category FROM Loan l JOIN l.book l2 WHERE l.user.id = :userId) " +
            "AND l.id NOT IN (SELECT l.book.id FROM Loan l WHERE l.user.id = :userId)")
    Page<Book> findRecommendedBooks(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn AND (:bookId IS NULL OR b.id <> :bookId)")
    Optional<Book> findByIsbnAndIgnoreSameId(@Param("isbn") String isbn, @Param("bookId") Long bookId);

    @Query("SELECT b FROM Book b LEFT JOIN Loan l ON l.book.id = b.id WHERE l.status IS NULL OR l.status != 'EMPRESTADO'")    List<Book> findBooksWithoutLoan();

}
