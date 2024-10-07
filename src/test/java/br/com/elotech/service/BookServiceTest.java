package br.com.elotech.service;

import br.com.elotech.entity.Book;
import br.com.elotech.entity.User;
import br.com.elotech.repository.BookRepository;
import br.com.elotech.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookServiceTest {


    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService service;

    private User user;
    private Book recommendedBook;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("teste", "teste@gmail.com", LocalDate.now(), "123456789");
        user.setId(1L);

        book = new Book("Title Teste", "Name Author Teste", "categoria", "12345",LocalDate.of(2008, 8, 1));
        book.setId(1L);

        recommendedBook = new Book("Title Teste2", "Name Author Teste", "categoria", "123456",LocalDate.of(2008, 8, 1));
        book.setId(2L);
    }

    @Test
    void shouldReturnRecommendedBooks() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> recommendedBooksPage = new PageImpl<>(List.of(recommendedBook));

        when(bookRepository.findRecommendedBooks(1L, pageable)).thenReturn(recommendedBooksPage);

        Page<Book> recommendations = service.getAllRecommendedBooks(user.getId(), pageable);

        assertEquals(1, recommendations.getTotalElements());
        assertEquals(recommendedBook.getTitle(), recommendations.getContent().get(0).getTitle());
    }


}

