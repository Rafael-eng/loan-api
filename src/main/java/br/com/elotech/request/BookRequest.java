package br.com.elotech.request;

import java.time.LocalDate;

public record BookRequest(
        Long id,
        String title,
        String author,
        String category,
        String isbn,
        LocalDate publicationDate) {
}
