package br.com.elotech.controller;

import br.com.elotech.entity.Loan;
import br.com.elotech.mapper.LoanMapper;
import br.com.elotech.request.LoanRequest;
import br.com.elotech.response.LoanResponse;
import br.com.elotech.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@Validated
public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanMapper loanMapper;


    @PostMapping
    public ResponseEntity<Void> loanBook(@RequestParam Long userId, @RequestParam Long bookId) {
        loanService.realizeloanBook(userId, bookId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<LoanResponse> updateLoan(
            @RequestBody LoanRequest loanRequest) {
         loanService.updateLoan(loanRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<LoanResponse>>> getAllBooks(
            @PageableDefault(page = 0, size = 5) Pageable pageable,
            PagedResourcesAssembler<LoanResponse> pagedAssembler) {

        Page<Loan> pageResult = loanService.getAllLoans(pageable);
        Page<LoanResponse> responsePage = loanMapper.toPageResponse(pageResult);
        return ResponseEntity.ok(pagedAssembler.toModel(responsePage));
    }

}