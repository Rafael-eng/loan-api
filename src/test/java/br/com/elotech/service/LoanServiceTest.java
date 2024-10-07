package br.com.elotech.service;

import br.com.elotech.entity.Book;
import br.com.elotech.entity.Loan;
import br.com.elotech.entity.User;
import br.com.elotech.repository.BookRepository;
import br.com.elotech.repository.LoanRepository;
import br.com.elotech.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    private User user;
    private Book book;
    private Loan loan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User( "teste", "teste@gmail.com", LocalDate.now(), "123456789");
        user.setId(1L);
        book = new Book("Title Teste", "Name Author Teste", "Categoria", "123456", LocalDate.of(2008, 8, 1));
        book.setId(1L);
        loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(10));
        loan.setStatus("EMPRESTADO");
    }

    @Test
    void testRealizeloanBook_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan result = loanService.realizeloanBook(1L, 1L);

        verify(userRepository).findById(1L);
        verify(bookRepository).findById(1L);
        verify(loanRepository).save(any(Loan.class));

        assertNotNull(result);
        assertEquals("EMPRESTADO", result.getStatus());
        assertEquals(user, result.getUser());
        assertEquals(book, result.getBook());
        assertEquals(LocalDate.now(), result.getLoanDate());
        assertEquals(LocalDate.now().plusDays(10), result.getReturnDate());
    }

    @Test
    void testRealizeloanBook_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loanService.realizeloanBook(1L, 1L);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());

        verify(bookRepository, never()).findById(anyLong());
    }

    @Test
    void testRealizeloanBook_BookNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loanService.realizeloanBook(1L, 1L);
        });

        assertEquals("Livro não encontrado", exception.getMessage());

        verify(loanRepository, never()).save(any(Loan.class));
    }
}
