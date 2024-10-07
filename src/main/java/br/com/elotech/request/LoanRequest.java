package br.com.elotech.request;

import java.time.LocalDate;

public record LoanRequest(
        Long id,
        String status,
        LocalDate returnDate
) {}
