package digital.gtech.onlineshop.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesReportDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SalesReportRequest extends Paging.PagingRequest {
        private LocalDate startDate;
        private LocalDate endDate;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SalesReportResponse {
        private String productId;
        private String productName;
        private String productType;
        private BigDecimal salesPrice;
        private BigDecimal margin;
    }
}
