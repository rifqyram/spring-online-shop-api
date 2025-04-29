package digital.gtech.onlineshop.service.impl;

import digital.gtech.onlineshop.dto.SalesReportDTO;
import digital.gtech.onlineshop.entity.TransactionDetail;
import digital.gtech.onlineshop.repository.TransactionDetailRepository;
import digital.gtech.onlineshop.service.SalesReportService;
import digital.gtech.onlineshop.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {
    private final TransactionDetailRepository transactionDetailRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<SalesReportDTO.SalesReportResponse> getSalesReport(SalesReportDTO.SalesReportRequest request) {
        Pageable pageable = PageUtil.createPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortBy(), TransactionDetail.class);
        Page<TransactionDetail> salesReportData = transactionDetailRepository.findSalesReportData(request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay(), pageable);

        return salesReportData.map(sales -> {
            BigDecimal marginFee = sales.getMargin().getMarginPercentage().divide(BigDecimal.valueOf(100), MathContext.DECIMAL128)
                    .multiply(sales.getProduct().getPrice());
            BigDecimal salesPrice = sales.getProduct().getPrice().add(marginFee, MathContext.DECIMAL128);

            return SalesReportDTO.SalesReportResponse.builder()
                    .productId(sales.getProduct().getId())
                    .productName(sales.getProduct().getName())
                    .productType(sales.getProduct().getProductType().getType())
                    .margin(marginFee)
                    .salesPrice(salesPrice)
                    .build();
        });
    }
}
