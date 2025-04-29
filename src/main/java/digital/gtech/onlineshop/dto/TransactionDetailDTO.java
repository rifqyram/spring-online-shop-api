package digital.gtech.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDetailDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NewTransactionDetail {
        private String productId;
        private Integer quantity;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TransactionDetailResponse {
        private String id;
        private String transactionId;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subTotal;
        private BigDecimal margin;
    }

}
