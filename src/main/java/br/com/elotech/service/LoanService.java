package br.com.elotech.service;

import br.com.elotech.entity.Book;
import br.com.elotech.entity.Loan;
import br.com.elotech.entity.User;
import br.com.elotech.mapper.LoanMapper;
import br.com.elotech.repository.BookRepository;
import br.com.elotech.repository.LoanRepository;
import br.com.elotech.repository.UserRepository;
import br.com.elotech.request.LoanRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public Loan realizeloanBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(10));
        loan.setStatus("EMPRESTADO");

        return loanRepository.save(loan);
    }

    public Page<Loan> getAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }

    @Transactional
    public void updateLoan(LoanRequest updateRequest) {
        loanRepository.updateLoanPartial(updateRequest.id(), updateRequest.status(), updateRequest.returnDate());
    }
}
