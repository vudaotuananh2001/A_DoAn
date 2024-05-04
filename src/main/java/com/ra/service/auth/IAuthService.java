package com.ra.service.auth;

import com.ra.models.entity.EnumDescriptionProduct;
import com.ra.models.entity.Product;

import java.util.List;

public interface IAuthService {
    List<Product> getAll();
    List<Product> getProductNewProduct(EnumDescriptionProduct description);
    List<Product> getProductByFavoriteProduct(EnumDescriptionProduct favoriteProduct);
}
