package digital.gtech.onlineshop.controller;

import digital.gtech.onlineshop.constant.ApiUrl;
import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.dto.TransactionDTO;
import digital.gtech.onlineshop.service.TransactionService;
import digital.gtech.onlineshop.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = ApiUrl.TRANSACTION)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createNewTransaction(@RequestBody TransactionDTO.NewTransactionRequest request) {
        TransactionDTO.TransactionResponse transactionResponse = transactionService.create(request);
        return ResponseUtil.buildResponse(
                HttpStatus.CREATED,
                ResponseMessage.TRANSACTION_CREATED_SUCCESS,
                transactionResponse
        );
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getById(@PathVariable String id) {
        TransactionDTO.TransactionResponse transactionResponse = transactionService.getById(id);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.TRANSACTION_GET_SUCCESS,
                transactionResponse
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    ) {
        Paging.PagingRequest pagingRequest = Paging.PagingRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<TransactionDTO.TransactionResponse> transactionResponses = transactionService.getAll(pagingRequest);
        return ResponseUtil.buildPageResponse(
                HttpStatus.OK,
                ResponseMessage.TRANSACTION_GET_SUCCESS,
                transactionResponses
        );
    }
}
