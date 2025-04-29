package digital.gtech.onlineshop.controller;

import digital.gtech.onlineshop.constant.ApiUrl;
import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.MarginPeriodDTO;
import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.service.MarginPeriodService;
import digital.gtech.onlineshop.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = ApiUrl.MARGIN_PERIOD_URL)
@RequiredArgsConstructor
public class MarginPeriodController {
    private final MarginPeriodService marginPeriodService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createNewProduct(@RequestBody MarginPeriodDTO.NewMarginPeriodRequest request) {
        MarginPeriodDTO.MarginPeriodResponse marginPeriodResponse = marginPeriodService.create(request);
        return ResponseUtil.buildResponse(
                HttpStatus.CREATED,
                ResponseMessage.MARGIN_PERIOD_CREATED_SUCCESS,
                marginPeriodResponse
        );
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getById(@PathVariable String id) {
        MarginPeriodDTO.MarginPeriodResponse marginPeriodResponse = marginPeriodService.getById(id);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.MARGIN_PERIOD_GET_SUCCESS,
                marginPeriodResponse
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
        Page<MarginPeriodDTO.MarginPeriodResponse> marginPeriodResponses = marginPeriodService.getAll(pagingRequest);
        return ResponseUtil.buildPageResponse(
                HttpStatus.OK,
                ResponseMessage.MARGIN_PERIOD_GET_SUCCESS,
                marginPeriodResponses
        );
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateProduct(@RequestBody MarginPeriodDTO.UpdateMarginPeriodRequest request) {
        MarginPeriodDTO.MarginPeriodResponse marginPeriodResponse = marginPeriodService.update(request);
        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.MARGIN_PERIOD_UPDATED_SUCCESS,
                marginPeriodResponse
        );
    }


}
