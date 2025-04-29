package digital.gtech.onlineshop.service.impl;

import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.ProductTypeDTO;
import digital.gtech.onlineshop.entity.ProductType;
import digital.gtech.onlineshop.repository.ProductTypeRepository;
import digital.gtech.onlineshop.service.ProductTypeService;
import digital.gtech.onlineshop.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductTypeDTO.ProductTypeResponse create(ProductTypeDTO.NewProductTypeRequest request) {
        validationUtil.validate(request);
        ProductType productType = ProductType.builder()
                .type(request.getType())
                .build();
        productTypeRepository.saveAndFlush(productType);
        return convertToResponse(productType);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductTypeDTO.ProductTypeResponse getById(String id) {
        return convertToResponse(findByIdOrThrowNotFound(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductTypeDTO.ProductTypeResponse> getAll() {
        return productTypeRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductTypeDTO.ProductTypeResponse update(ProductTypeDTO.UpdateProductTypeRequest request) {
        validationUtil.validate(request);
        ProductType productType = findByIdOrThrowNotFound(request.getId());
        productType.setType(request.getType());
        productTypeRepository.saveAndFlush(productType);
        return convertToResponse(productType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        ProductType productType = findByIdOrThrowNotFound(id);
        productTypeRepository.delete(productType);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductType getOne(String id) {
        return findByIdOrThrowNotFound(id);
    }

    private ProductType findByIdOrThrowNotFound(String id) {
        return productTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.PRODUCT_TYPE_ERROR_NOT_FOUND));
    }

    private ProductTypeDTO.ProductTypeResponse convertToResponse(ProductType productType) {
        return ProductTypeDTO.ProductTypeResponse.builder()
                .id(productType.getId())
                .type(productType.getType())
                .build();
    }
}
