package br.com.elotech.mapper;

import br.com.elotech.entity.Loan;
import br.com.elotech.response.LoanResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

        LoanResponse toLoanResponse(Loan loan);

        default Page<LoanResponse> toPageResponse(Page<Loan> page) {
                List<LoanResponse> bookResponses = new ArrayList<>();
                for (Loan loan : page.getContent()) {
                        bookResponses.add(toLoanResponse(loan));
                }
                return new PageImpl<>(bookResponses, page.getPageable(), page.getTotalElements());
        }
}
