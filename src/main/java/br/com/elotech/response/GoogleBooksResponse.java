package br.com.elotech.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record GoogleBooksResponse(@JsonProperty("totalItems")
                                  int totalItemGoogleBooksResponses,
                                  List<ItemGoogleBooksResponse> items,
                                  int page,
                                  int pageSize) {
}
