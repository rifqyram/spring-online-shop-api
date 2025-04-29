package digital.gtech.onlineshop.service;

import digital.gtech.onlineshop.dto.Paging;
import digital.gtech.onlineshop.dto.ProductDTO;
import digital.gtech.onlineshop.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDTO.ProductResponse create(ProductDTO.NewProductRequest request);
    ProductDTO.ProductResponse getById(String id);
    Page<ProductDTO.ProductResponse> getAll(Paging.PagingRequest request);
    ProductDTO.ProductResponse update(ProductDTO.UpdateProductRequest request);
    void deleteById(String id);

    Product getOne(String id);
    Product update(Product product);
}
