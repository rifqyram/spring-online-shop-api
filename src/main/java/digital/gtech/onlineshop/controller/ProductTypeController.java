package digital.gtech.onlineshop.controller;

import digital.gtech.onlineshop.constant.ApiUrl;
import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.ProductTypeDTO;
import digital.gtech.onlineshop.service.ProductTypeService;
import digital.gtech.onlineshop.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ApiUrl.PRODUCT_TYPE)
@RequiredArgsConstructor
public class ProductTypeController {
    private final ProductTypeService productTypeService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createNewProduct(@RequestBody ProductTypeDTO.NewProductTypeRequest request) {
        ProductTypeDTO.ProductTypeResponse productTypeResponse = productTypeService.create(request);
        return ResponseUtil.buildResponse(
                HttpStatus.CREATED,
                ResponseMessage.PRODUCT_TYPE_CREATED_SUCCESS,
                productTypeResponse
        );
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getById(@PathVariable String id) {
        ProductTypeDTO.ProductTypeResponse productTypeResponse = productTypeService.getById(id);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.PRODUCT_TYPE_GET_SUCCESS,
                productTypeResponse
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<ProductTypeDTO.ProductTypeResponse> productTypeResponses = productTypeService.getAll();
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.PRODUCT_TYPE_GET_SUCCESS,
                productTypeResponses
        );
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateProduct(@RequestBody ProductTypeDTO.UpdateProductTypeRequest request) {
        ProductTypeDTO.ProductTypeResponse productTypeResponse = productTypeService.update(request);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.PRODUCT_TYPE_UPDATED_SUCCESS,
                productTypeResponse
        );
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateProduct(@PathVariable String id) {
        productTypeService.deleteById(id);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.PRODUCT_TYPE_DELETED_SUCCESS,
                null
        );
    }
}
