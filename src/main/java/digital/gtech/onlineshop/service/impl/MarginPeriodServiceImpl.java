package digital.gtech.onlineshop.service.impl;

import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.MarginPeriodDTO;
import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.entity.MarginPeriod;
import digital.gtech.onlineshop.repository.MarginPeriodRepository;
import digital.gtech.onlineshop.service.MarginPeriodService;
import digital.gtech.onlineshop.util.PageUtil;
import digital.gtech.onlineshop.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarginPeriodServiceImpl implements MarginPeriodService {
    private final MarginPeriodRepository marginPeriodRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MarginPeriodDTO.MarginPeriodResponse create(MarginPeriodDTO.NewMarginPeriodRequest request) {
        validationUtil.validate(request);
        validateNoOverlap(request.getStartDate(), request.getEndDate());

        MarginPeriod marginPeriod = MarginPeriod.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .marginPercentage(request.getMarginPercentage())
                .isActive(true)
                .build();
        marginPeriodRepository.saveAndFlush(marginPeriod);
        return convertToResponse(marginPeriod);
    }

    @Transactional(readOnly = true)
    @Override
    public MarginPeriodDTO.MarginPeriodResponse getById(String id) {
        return convertToResponse(findByIdOrThrowNotFound(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MarginPeriodDTO.MarginPeriodResponse> getAll(Paging.PagingRequest request) {
        Pageable pageable = PageUtil.createPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortBy(), MarginPeriod.class);
        return marginPeriodRepository.findAll(pageable).map(this::convertToResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MarginPeriodDTO.MarginPeriodResponse update(MarginPeriodDTO.UpdateMarginPeriodRequest request) {
        MarginPeriod currentMargin = findByIdOrThrowNotFound(request.getId());
        currentMargin.setIsActive(false);
        marginPeriodRepository.saveAndFlush(currentMargin);

        MarginPeriod newMarginPeriod = MarginPeriod.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .marginPercentage(request.getMarginPercentage())
                .isActive(true)
                .build();
        marginPeriodRepository.saveAndFlush(newMarginPeriod);

        return convertToResponse(newMarginPeriod);
    }

    @Transactional(readOnly = true)
    @Override
    public MarginPeriod getActiveMargin() {
        return marginPeriodRepository.findActiveMargin(LocalDate.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.MARGIN_PERIOD_ERROR_NOT_FOUND));
    }

    private void validateNoOverlap(LocalDate startDate, LocalDate endDate) {
        List<MarginPeriod> overlaps = marginPeriodRepository.findOverlap(startDate, endDate);
        if (!overlaps.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ResponseMessage.MARGIN_PERIOD_ERROR_OVERLAP);
        }
    }

    private MarginPeriod findByIdOrThrowNotFound(String id) {
        return marginPeriodRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.MARGIN_PERIOD_ERROR_NOT_FOUND));
    }

    private MarginPeriodDTO.MarginPeriodResponse convertToResponse(MarginPeriod marginPeriod) {
        return MarginPeriodDTO.MarginPeriodResponse.builder()
                .id(marginPeriod.getId())
                .startDate(marginPeriod.getStartDate())
                .endDate(marginPeriod.getEndDate())
                .marginPercentage(marginPeriod.getMarginPercentage())
                .createdAt(marginPeriod.getCreatedAt().toString())
                .updatedAt(marginPeriod.getUpdatedAt().toString())
                .build();
    }
}
