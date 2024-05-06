package br.dev.mhc.financialassistant.transaction.controllers;

import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.services.interfaces.ICreateTransactionService;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IDeleteTransactionByIdAndUserIdService;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IFindTransactionsByUserIdService;
import br.dev.mhc.financialassistant.transaction.services.interfaces.IUpdateTransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.dev.mhc.financialassistant.common.constants.RouteConstants.TRANSACTIONS_ROUTE;
import static br.dev.mhc.financialassistant.common.constants.RouteConstants.USERS_ROUTE;

@RestController
@RequestMapping(value = USERS_ROUTE + "/{userId}" + TRANSACTIONS_ROUTE)
public class UserTransactionController {

    private final ICreateTransactionService createTransactionService;
    private final IUpdateTransactionService updateTransactionService;
    private final IDeleteTransactionByIdAndUserIdService deleteTransactionByIdAndUserIdService;
    private final IFindTransactionsByUserIdService findUserTransactionsService;

    public UserTransactionController(ICreateTransactionService createTransactionService, IUpdateTransactionService updateTransactionService, IDeleteTransactionByIdAndUserIdService deleteTransactionByIdAndUserIdService, IFindTransactionsByUserIdService findUserTransactionsService) {
        this.createTransactionService = createTransactionService;
        this.updateTransactionService = updateTransactionService;
        this.deleteTransactionByIdAndUserIdService = deleteTransactionByIdAndUserIdService;
        this.findUserTransactionsService = findUserTransactionsService;
    }

    @PostMapping
    ResponseEntity<TransactionDTO> create(@RequestBody @Valid TransactionDTO transactionDTO) {
        transactionDTO = createTransactionService.create(transactionDTO);
        return ResponseEntity.created(URIUtils.buildUri(transactionDTO.getId())).body(transactionDTO);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<TransactionDTO> update(@RequestBody @Valid TransactionDTO transactionDTO) {
        transactionDTO = updateTransactionService.update(transactionDTO);
        return ResponseEntity.ok(transactionDTO);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long id) {
        deleteTransactionByIdAndUserIdService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    ResponseEntity<Page<TransactionDTO>> getByUser(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(findUserTransactionsService.findPageable(userId, pageable));
    }

}
