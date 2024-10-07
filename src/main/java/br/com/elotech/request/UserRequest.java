package br.com.elotech.request;

import java.time.LocalDate;

public record UserRequest(
        Long id,
        String name,
        String email,
        LocalDate registrationDate,
        String phone
) {
}
