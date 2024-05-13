package com.ra.service.user.shopping_cart;

import com.ra.models.dto.request.ShoppingCartRequest;
import com.ra.models.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> getAll(Long id);
    ShoppingCart add(ShoppingCartRequest shoppingCartRequest,Long userId);
    ShoppingCart save(ShoppingCart shoppingCart);
    ShoppingCart findShoppingCartById(Long product_id,Long userId);
    void deleteById(Long id);

}
