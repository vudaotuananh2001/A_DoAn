package com.ra.service.admin.product;

import com.ra.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Product save(Product product);
    Product findById(Long id);
    void deleteById(Long id);
    Page<Product> getAllPage(Integer pageNo,String search);
    Page<Product> getAllPageUser(Integer pageNo);
    Page<Product> getAllProductByCategoryId(Long id,Integer pageNo);
}
