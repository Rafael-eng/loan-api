package br.com.elotech.service;

import br.com.elotech.exception.EmprestimoApiException;
import br.com.elotech.response.GoogleBooksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GoogleBooksService {

    @Value("${google.books.api-url}")
    private String apiUrl;
    @Value("${google.books.api-key}")
    private String apiKey;


    public Object searchBooks(String title, int page, int pageSize) throws EmprestimoApiException {
        RestTemplate restTemplate = new RestTemplate();
        int startIndex = page * pageSize;

        String url = String.format("%s?q=%s&key=%s&startIndex=%d&maxResults=%d",
                apiUrl,
                title,
                apiKey,
                startIndex,
                pageSize);
        try {
            var response = restTemplate.getForObject(url, GoogleBooksResponse.class);
                int totalItems = Objects.requireNonNull(response).totalItemGoogleBooksResponses();

            Map<String, Object> paginatedResponse = new HashMap<>();
            paginatedResponse.put("totalElements", totalItems);
            paginatedResponse.put("items", response.items());
            paginatedResponse.put("page", page);
            paginatedResponse.put("pageSize", pageSize);

            return paginatedResponse;
        } catch (HttpClientErrorException e) {
            throw new EmprestimoApiException("Erro ao buscar livros: " + e.getMessage(), e);
        } catch (RestClientException e) {
            throw new EmprestimoApiException("Erro ao acessar a API do Google Books: " + e.getMessage(), e);
        }
    }

}