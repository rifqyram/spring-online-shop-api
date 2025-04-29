package digital.gtech.onlineshop.service;

import digital.gtech.onlineshop.dto.MarginPeriodDTO;
import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.entity.MarginPeriod;
import org.springframework.data.domain.Page;

public interface MarginPeriodService {
    MarginPeriodDTO.MarginPeriodResponse create(MarginPeriodDTO.NewMarginPeriodRequest request);
    MarginPeriodDTO.MarginPeriodResponse getById(String id);
    Page<MarginPeriodDTO.MarginPeriodResponse> getAll(Paging.PagingRequest request);
    MarginPeriodDTO.MarginPeriodResponse update(MarginPeriodDTO.UpdateMarginPeriodRequest request);

    MarginPeriod getActiveMargin();
}
