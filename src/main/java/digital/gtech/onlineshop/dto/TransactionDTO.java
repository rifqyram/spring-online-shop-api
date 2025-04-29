package digital.gtech.onlineshop.dto;

import digital.gtech.onlineshop.entity.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class TransactionDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NewTransactionRequest {
        private String customerEmail;
        private List<TransactionDetailDTO.NewTransactionDetail> transactionDetails;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TransactionResponse {
        private String id;
        private String customerEmail;
        private BigDecimal shippingFee;
        private BigDecimal totalAmount;
        private BigDecimal taxAmount;
        private String transactionDate;
        private String createdAt;
        private String updatedAt;
        private List<TransactionDetailDTO.TransactionDetailResponse> transactionDetails;
    }
}
