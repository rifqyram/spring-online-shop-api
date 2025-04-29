package digital.gtech.onlineshop.service.impl;

import digital.gtech.onlineshop.constant.ResponseMessage;
import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.dto.ProductDTO;
import digital.gtech.onlineshop.entity.Product;
import digital.gtech.onlineshop.entity.ProductType;
import digital.gtech.onlineshop.repository.ProductRepository;
import digital.gtech.onlineshop.service.ProductService;
import digital.gtech.onlineshop.service.ProductTypeService;
import digital.gtech.onlineshop.util.PageUtil;
import digital.gtech.onlineshop.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;
    private final ProductTypeService productTypeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductDTO.ProductResponse create(ProductDTO.NewProductRequest request) {
        validationUtil.validate(request);
        ProductType productType = productTypeService.getOne(request.getProductTypeId());
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .productType(productType)
                .build();
        productRepository.saveAndFlush(product);
        return convertToResponse(product);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO.ProductResponse getById(String id) {
        return convertToResponse(findByIdOrThrowNotFound(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDTO.ProductResponse> getAll(Paging.PagingRequest request) {
        Pageable pageable = PageUtil.createPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortBy(), Product.class);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::convertToResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductDTO.ProductResponse update(ProductDTO.UpdateProductRequest request) {
        validationUtil.validate(request);
        Product product = findByIdOrThrowNotFound(request.getId());

        if (!product.getProductType().getId().equals(request.getProductTypeId())) {
            ProductType productType = productTypeService.getOne(request.getProductTypeId());
            product.setProductType(productType);
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        productRepository.saveAndFlush(product);
        return convertToResponse(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Product product = findByIdOrThrowNotFound(id);
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Product getOne(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public Product update(Product product) {
        findByIdOrThrowNotFound(product.getId());
        return productRepository.saveAndFlush(product);
    }

    private Product findByIdOrThrowNotFound(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.PRODUCT_ERROR_NOT_FOUND));
    }

    private ProductDTO.ProductResponse convertToResponse(Product product) {
        return ProductDTO.ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .productType(product.getProductType().getType())
                .build();
    }
}
