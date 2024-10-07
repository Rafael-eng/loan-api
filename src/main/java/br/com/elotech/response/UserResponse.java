package br.com.elotech.response;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDate registrationDate,
        String phone
) {
}
