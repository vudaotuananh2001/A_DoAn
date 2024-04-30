package com.ra.service.auth;

import com.ra.models.entity.Product;
import com.ra.repository.admin.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthServiceImpl implements  IAuthService{
    @Autowired
    private IProductRepository productRepository;
    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
