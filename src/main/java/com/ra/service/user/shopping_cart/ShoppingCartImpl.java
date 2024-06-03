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
    public ShoppingCart add(ShoppingCartRequest shoppingCartRequest, Long userId) {
        Product product = productService.findById(shoppingCartRequest.getProductId());
        ShoppingCart existingCart = shoppingCartRepository.findByUserIdAndProductId(userId, shoppingCartRequest.getProductId());

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + 1);
            shoppingCartRepository.save(existingCart);
        } else {
            if (product.getQuantity() < shoppingCartRequest.getQuantity()) {
                throw new RuntimeException("Số lượng không đủ");
            }
            User user = userService.findById(userId);
            ShoppingCart shoppingCart = ShoppingCart.builder()
                    .product(product)
                    .user(user)
                    .quantity(shoppingCartRequest.getQuantity())
                    .build();
            existingCart = shoppingCartRepository.save(shoppingCart); // Lưu giỏ hàng mới và gán lại cho existingCart
        }

        // Giảm số lượng sản phẩm trong kho
        product.setQuantity(product.getQuantity() - shoppingCartRequest.getQuantity());
        productService.save(product);

        return existingCart;
    }


    @Override
    public ShoppingCart save
        (ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart findShoppingCartById(Long userId, Long product_id) {
        return shoppingCartRepository.findByUserIdAndProductId(userId,product_id);
    }

    @Override
    public void deleteById(Long id) {
    shoppingCartRepository.deleteById(id);
    }
}
