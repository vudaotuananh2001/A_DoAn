package com.ra.service.user.shopping_cart;

import com.ra.models.dto.request.ShoppingCartRequest;
import com.ra.models.entity.Product;
import com.ra.models.entity.ShoppingCart;
import com.ra.models.entity.User;
import com.ra.repository.user.ShoppingCartRepository;
import com.ra.repository.user.UserRepository;
import com.ra.security.UserPrincipal;
import com.ra.service.admin.product.IProductService;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IProductService productService;
    @Autowired
    private UserService userService;

    @Override
    public List<ShoppingCart> getAll(Long id) {
        return  shoppingCartRepository.findByUser(id);
    }

    @Override
    public ShoppingCart add(ShoppingCartRequest shoppingCartRequest,Long userId) {
        Product product  = productService.findById(shoppingCartRequest.getProductId());
        User user = userService.findById(userId);
        ShoppingCart shoppingCart =  ShoppingCart.builder()
                .product(product)
                .user(user)
                .quantity(shoppingCartRequest.getQuantity())
                .build();
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public ShoppingCart findShoppingCartById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    shoppingCartRepository.deleteById(id);
    }
}
