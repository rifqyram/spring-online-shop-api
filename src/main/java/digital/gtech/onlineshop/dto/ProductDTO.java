package digital.gtech.onlineshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class ProductDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NewProductRequest {
        @NotBlank(message = "name cannot be null or empty")
        private String name;

        @NotNull(message = "price cannot be null")
        @Min(value = 0, message = "price cannot be less than zero")
        private BigDecimal price;

        @NotNull(message = "stock cannot be null")
        @Min(value = 0, message = "stock cannot be less than zero")
        private Integer stock;

        @NotBlank(message = "product type id cannot be null or empty")
        private String productTypeId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateProductRequest {
        @NotBlank(message = "id cannot be null or empty")
        private String id;

        @NotBlank(message = "name cannot be null or empty")
        private String name;

        @NotNull(message = "price cannot be null")
        @Min(value = 0, message = "price cannot be less than zero")
        private BigDecimal price;

        @NotNull(message = "stock cannot be null")
        @Min(value = 0, message = "stock cannot be less than zero")
        private Integer stock;

        @NotBlank(message = "product type id cannot be null or empty")
        private String productTypeId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductResponse {
        private String id;
        private String name;
        private BigDecimal price;
        private Integer stock;
        private String productType;
    }

}
