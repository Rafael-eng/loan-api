package br.com.elotech.repository;

import br.com.elotech.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Modifying
    @Query("UPDATE Loan l SET " +
            "l.status = COALESCE(:status, l.status), " +
            "l.returnDate = COALESCE(:returnDate, l.returnDate) " +
            "WHERE l.id = :id")
    void updateLoanPartial(@Param("id") Long id,
                          @Param("status") String status,
                          @Param("returnDate") LocalDate returnDate);

}
