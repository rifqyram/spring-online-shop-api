package digital.gtech.onlineshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MarginPeriodDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NewMarginPeriodRequest {
        @NotNull(message = "start date cannot be null")
        private LocalDate startDate;
        @NotNull(message = "end date cannot be null")
        private LocalDate endDate;
        @NotNull(message = "margin percentage cannot be null")
        private BigDecimal marginPercentage;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateMarginPeriodRequest {
        private String id;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal marginPercentage;
        private Boolean isActive;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MarginPeriodResponse {
        private String id;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal marginPercentage;
        private String createdAt;
        private String updatedAt;
    }

}
