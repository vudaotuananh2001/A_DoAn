package com.ra.service.admin.product;

import com.ra.models.entity.Product;
import com.ra.repository.admin.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<Product> getAllPage(Integer pageNo, String search) {
        Pageable pageable = PageRequest.of(pageNo-1,4);
        if(search!=null && !search.isEmpty()){
           return  productRepository.findProductByProductNameLike("%" +search+"%",pageable);
        }
        return productRepository.findAll(pageable);
    }
    
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
