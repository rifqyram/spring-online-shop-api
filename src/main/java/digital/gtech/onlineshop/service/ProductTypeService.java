package digital.gtech.onlineshop.service;

import digital.gtech.onlineshop.dto.ProductTypeDTO;
import digital.gtech.onlineshop.entity.ProductType;

import java.util.List;

public interface ProductTypeService {
    ProductTypeDTO.ProductTypeResponse create(ProductTypeDTO.NewProductTypeRequest request);
    ProductTypeDTO.ProductTypeResponse getById(String id);
    List<ProductTypeDTO.ProductTypeResponse> getAll();
    ProductTypeDTO.ProductTypeResponse update(ProductTypeDTO.UpdateProductTypeRequest request);
    void deleteById(String id);

    ProductType getOne(String id);
}
