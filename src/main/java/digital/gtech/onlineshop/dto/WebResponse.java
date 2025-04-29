package digital.gtech.onlineshop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
    private Paging.PagingResponse paging;
}
