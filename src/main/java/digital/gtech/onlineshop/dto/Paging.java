package digital.gtech.onlineshop.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

public class Paging {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    public static class PagingRequest {
        private Integer page;
        private Integer size;
        private String sortBy;
        private String direction;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PagingResponse {
        private Integer totalPages;
        private Long count;
        private Integer page;
        private Integer size;
        private Boolean hasNext;
        private Boolean hasPrevious;
    }
}
