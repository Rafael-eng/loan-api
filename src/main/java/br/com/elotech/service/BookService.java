package br.com.elotech.service;

import br.com.elotech.entity.Book;
import br.com.elotech.mapper.BookMapper;
import br.com.elotech.repository.BookRepository;
import br.com.elotech.request.BookRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    public Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    public Page<Book> getAllRecommendedBooks(Long userId, Pageable pageable) {
        return bookRepository.findRecommendedBooks(userId, pageable);
    }

    public List<Book> getBooksWithoutLoan() {
        return bookRepository.findBooksWithoutLoan();
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book createBook(BookRequest createRequest) {
        Book book = bookMapper.toEntity(createRequest);
        return bookRepository.save(book);
    }

    public Book updateBook(BookRequest updateRequest) {
        return getBook(updateRequest.id()).map(book -> {
            Book updatedBook = bookMapper.toEntity(updateRequest);
            return bookRepository.save(updatedBook);
        }).orElseThrow(() -> new EntityNotFoundException("Livro com o ID " + updateRequest.id() + " não encontrado"));    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public boolean existsByIsbn(String isbn, Long bookId) {
        return bookRepository.findByIsbnAndIgnoreSameId(isbn, bookId).isPresent();
    }

}
