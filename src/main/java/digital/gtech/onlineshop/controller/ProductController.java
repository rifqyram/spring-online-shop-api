package digital.gtech.onlineshop.controller;

import digital.gtech.onlineshop.constant.ApiUrl;
import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.dto.ProductDTO;
import digital.gtech.onlineshop.service.ProductService;
import digital.gtech.onlineshop.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = ApiUrl.PRODUCT)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createNewProduct(@RequestBody ProductDTO.NewProductRequest request) {
        ProductDTO.ProductResponse productResponse = productService.create(request);
        return ResponseUtil.buildResponse(
                HttpStatus.CREATED,
                ResponseMessage.PRODUCT_CREATED_SUCCESS,
                productResponse
        );
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getById(@PathVariable String id) {
        ProductDTO.ProductResponse productResponse = productService.getById(id);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.PRODUCT_GET_SUCCESS,
                productResponse
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    ) {
        Paging.PagingRequest pagingRequest = Paging.PagingRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<ProductDTO.ProductResponse> productResponses = productService.getAll(pagingRequest);
        return ResponseUtil.buildPageResponse(
                HttpStatus.OK,
                ResponseMessage.PRODUCT_GET_SUCCESS,
                productResponses
        );
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO.UpdateProductRequest request) {
        ProductDTO.ProductResponse productResponse = productService.update(request);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.PRODUCT_UPDATED_SUCCESS,
                productResponse
        );
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateProduct(@PathVariable String id) {
        productService.deleteById(id);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.PRODUCT_DELETED_SUCCESS,
                null
        );
    }

}
