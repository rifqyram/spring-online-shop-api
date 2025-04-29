package digital.gtech.onlineshop.util;

import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.dto.WebResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {

    public static <T> ResponseEntity<WebResponse<T>> buildResponse(HttpStatus httpStatus, String message, T data) {
        WebResponse<T> response = WebResponse.<T>builder()
                .statusCode(httpStatus.value())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<WebResponse<List<T>>> buildPageResponse(HttpStatus httpStatus, String message, Page<T> data) {
        Paging.PagingResponse pagingResponse = Paging.PagingResponse.builder()
                .page(data.getPageable().getPageNumber() + 1)
                .size(data.getSize())
                .count(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .hasNext(data.hasNext())
                .hasPrevious(data.hasPrevious())
                .build();
        WebResponse<List<T>> response = WebResponse.<List<T>>builder()
                .statusCode(httpStatus.value())
                .message(message)
                .data(data.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

}
