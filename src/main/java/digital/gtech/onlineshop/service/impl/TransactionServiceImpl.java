package digital.gtech.onlineshop.service.impl;

import digital.gtech.onlineshop.config.TransactionConfig;
import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.dto.TransactionDTO;
import digital.gtech.onlineshop.dto.TransactionDetailDTO;
import digital.gtech.onlineshop.entity.MarginPeriod;
import digital.gtech.onlineshop.entity.Product;
import digital.gtech.onlineshop.entity.Transaction;
import digital.gtech.onlineshop.entity.TransactionDetail;
import digital.gtech.onlineshop.repository.TransactionRepository;
import digital.gtech.onlineshop.service.MarginPeriodService;
import digital.gtech.onlineshop.service.ProductService;
import digital.gtech.onlineshop.service.TransactionService;
import digital.gtech.onlineshop.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionConfig transactionConfig;
    private final ProductService productService;
    private final MarginPeriodService marginPeriodService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionDTO.TransactionResponse create(TransactionDTO.NewTransactionRequest request) {
        Transaction transaction = Transaction.builder()
                .customerEmail(request.getCustomerEmail())
                .shippingFee(transactionConfig.getShippingFee())
                .taxAmount(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.saveAndFlush(transaction);

        List<TransactionDetail> transactionDetails = new ArrayList<>();
        MarginPeriod activeMargin = marginPeriodService.getActiveMargin();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (TransactionDetailDTO.NewTransactionDetail td : request.getTransactionDetails()) {
            Product product = productService.getOne(td.getProductId());

            BigDecimal marginFee = getMarginFee(activeMargin, product);
            BigDecimal subTotal = (product.getPrice().add(marginFee)).multiply(BigDecimal.valueOf(td.getQuantity()));

            product.setStock(product.getStock() - td.getQuantity());
            productService.update(product);

            TransactionDetail transactionDetail = TransactionDetail.builder()
                    .transaction(transaction)
                    .product(product)
                    .quantity(td.getQuantity())
                    .price(product.getPrice())
                    .margin(activeMargin)
                    .subTotal(subTotal)
                    .build();
            transactionDetails.add(transactionDetail);
            totalAmount = totalAmount.add(subTotal);
        }

        transaction.setTransactionDetails(transactionDetails);

        BigDecimal taxAmount = totalAmount.multiply(transactionConfig.getTaxPercentage().divide(BigDecimal.valueOf(100), MathContext.DECIMAL128));
        transaction.setTaxAmount(taxAmount);
        transaction.setTotalAmount(totalAmount.add(taxAmount));
        transactionRepository.saveAndFlush(transaction);

        return convertToResponse(transaction);
    }
    @Transactional(readOnly = true)
    @Override
    public TransactionDTO.TransactionResponse getById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.TRANSACTION_ERROR_NOT_FOUND));
        return convertToResponse(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionDTO.TransactionResponse> getAll(Paging.PagingRequest request) {
        Pageable pageable = PageUtil.createPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortBy(), Transaction.class);
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(this::convertToResponse);
    }

    private TransactionDTO.TransactionResponse convertToResponse(Transaction transaction) {
        List<TransactionDetailDTO.TransactionDetailResponse> transactionDetailResponses = new ArrayList<>();

        for (TransactionDetail td : transaction.getTransactionDetails()) {
            transactionDetailResponses.add(TransactionDetailDTO.TransactionDetailResponse.builder()
                    .id(td.getId())
                    .transactionId(transaction.getId())
                    .price(td.getPrice())
                    .quantity(td.getQuantity())
                    .subTotal(td.getSubTotal())
                    .margin(getMarginFee(td.getMargin(), td.getProduct()))
                    .build());
        }

        return TransactionDTO.TransactionResponse.builder()
                .id(transaction.getId())
                .customerEmail(transaction.getCustomerEmail())
                .shippingFee(transaction.getShippingFee())
                .taxAmount(transaction.getTaxAmount())
                .totalAmount(transaction.getTotalAmount())
                .transactionDetails(transactionDetailResponses)
                .transactionDate(transaction.getTransactionDate().toString())
                .createdAt(transaction.getCreatedAt().toString())
                .updatedAt(transaction.getUpdatedAt().toString())
                .build();
    }

    private BigDecimal getMarginFee(MarginPeriod margin, Product product) {
        return margin.getMarginPercentage().divide(BigDecimal.valueOf(100), MathContext.DECIMAL128)
                .multiply(product.getPrice());
    }

}
