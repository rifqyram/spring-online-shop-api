package digital.gtech.onlineshop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductTypeDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NewProductTypeRequest {
        @NotBlank(message = "type cannot be null or empty")
        private String type;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateProductTypeRequest {
        @NotBlank(message = "id cannot be null or empty")
        private String id;

        @NotBlank(message = "type cannot be null or empty")
        private String type;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductTypeResponse {
        private String id;
        private String type;
    }
}
