package br.com.elotech.controller;

import br.com.elotech.entity.Book;
import br.com.elotech.exception.EmprestimoApiException;
import br.com.elotech.mapper.BookMapper;
import br.com.elotech.request.BookRequest;
import br.com.elotech.response.BookResponse;
import br.com.elotech.service.BookService;
import br.com.elotech.service.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/book")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private GoogleBooksService googleBooksService;
    @Autowired
    private BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest request) {
        Book createdBook = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.toResponse(createdBook));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Optional<Book> book = bookService.getBook(id);
        return book.map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<BookResponse> updateBook(@RequestBody BookRequest request) {
        Book updatedBook = bookService.updateBook(request);
        return ResponseEntity.ok(bookMapper.toResponse(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<BookResponse>>> getAllBooks(
            @PageableDefault(page = 0, size = 5) Pageable pageable,
            PagedResourcesAssembler<BookResponse> pagedAssembler) {

        Page<Book> pageResult = bookService.getAllBooks(pageable);
        Page<BookResponse> responsePage = bookMapper.toPageResponse(pageResult);
        return ResponseEntity.ok(pagedAssembler.toModel(responsePage));

    }

    @GetMapping("/recommendation")
    public ResponseEntity<PagedModel<EntityModel<BookResponse>>> getAllRecommendedBooks(
            @RequestParam Long userId,
            @PageableDefault(page = 0, size = 5) Pageable pageable,
            PagedResourcesAssembler<BookResponse> pagedAssembler) {

        Page<Book> pageResult = bookService.getAllRecommendedBooks(userId, pageable);
        Page<BookResponse> responsePage = bookMapper.toPageResponse(pageResult);
        return ResponseEntity.ok(pagedAssembler.toModel(responsePage));
    }

    @GetMapping("/without-lending")
    public ResponseEntity<List<BookResponse>> getBooksWithoutLending() {
        var books = bookService.getBooksWithoutLoan();
        return ResponseEntity.ok(bookMapper.toResponseList(books));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchBooksOnGoogleApi(@RequestParam String title,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int pageSize) throws EmprestimoApiException {
        var response = googleBooksService.searchBooks(title, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists-by-isbn/{isbn}")
    public ResponseEntity<Boolean> existsByIsbn(@PathVariable String isbn,
                                                @RequestParam(value = "bookId", required = false) Long bookId) {
        return ResponseEntity.ok(bookService.existsByIsbn(isbn, bookId));
    }

}
