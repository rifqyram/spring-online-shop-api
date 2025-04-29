package digital.gtech.onlineshop.service;

import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.dto.TransactionDTO;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionDTO.TransactionResponse create(TransactionDTO.NewTransactionRequest request);
    TransactionDTO.TransactionResponse getById(String id);
    Page<TransactionDTO.TransactionResponse> getAll(Paging.PagingRequest request);
}
