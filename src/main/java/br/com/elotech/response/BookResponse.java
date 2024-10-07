package br.com.elotech.response;

import java.time.LocalDate;

public record BookResponse(Long id,
                           String title,
                           String author,
                           String category,
                           String isbn,
                           LocalDate publicationDate) {
}
