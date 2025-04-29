package digital.gtech.onlineshop.controller;

import digital.gtech.onlineshop.constant.ApiUrl;
import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.SalesReportDTO;
import digital.gtech.onlineshop.service.SalesReportService;
import digital.gtech.onlineshop.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(path = ApiUrl.SALES_REPORT)
@RequiredArgsConstructor
public class SalesReportController {
    private final SalesReportService salesReportService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSalesReport(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction,
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate
    ) {
        SalesReportDTO.SalesReportRequest salesReportRequest = SalesReportDTO.SalesReportRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        Page<SalesReportDTO.SalesReportResponse> salesReport = salesReportService.getSalesReport(salesReportRequest);
        return ResponseUtil.buildPageResponse(
                HttpStatus.OK,
                ResponseMessage.SALES_REPORT_GET_SUCCESS,
                salesReport
        );
    }
}
