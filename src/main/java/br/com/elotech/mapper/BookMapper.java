package br.com.elotech.mapper;

import br.com.elotech.entity.Book;
import br.com.elotech.request.BookRequest;
import br.com.elotech.response.BookResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookRequest bookRequest);

    List<BookResponse> toResponseList(List<Book> bookRequest);

    BookResponse toResponse(Book book);

    default Page<BookResponse> toPageResponse(Page<Book> page) {
        List<BookResponse> bookResponses = new ArrayList<>();
        for (Book book : page.getContent()) {
            bookResponses.add(toResponse(book));
        }
        return new PageImpl<>(bookResponses, page.getPageable(), page.getTotalElements());
    }
}