package digital.gtech.onlineshop.service;

import digital.gtech.onlineshop.dto.SalesReportDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface SalesReportService {
    Page<SalesReportDTO.SalesReportResponse> getSalesReport(SalesReportDTO.SalesReportRequest request);
}
