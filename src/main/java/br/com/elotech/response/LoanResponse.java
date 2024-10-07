package br.com.elotech.response;

import java.time.LocalDate;

public record LoanResponse(
        Long id,
        UserResponse user,
        BookResponse book,
        LocalDate loanDate,
        LocalDate returnDate,
        String status
) {}
