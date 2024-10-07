package br.com.elotech.response;

import java.util.List;

public record ItemGoogleBooksResponse(VolumeInfo volumeInfo) {
    public record VolumeInfo(
            String title,
            List<String> authors,
            String publishedDate,
            List<IndustryIdentifier> industryIdentifiers
    ) {
        public record IndustryIdentifier(
                String identifier
        ) {}

    }
    }
