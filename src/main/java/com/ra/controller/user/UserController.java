package com.ra.controller.user;

import com.ra.models.entity.EnumDescriptionProduct;
import com.ra.models.entity.Product;
import com.ra.models.entity.ShoppingCart;
import com.ra.repository.user.ShoppingCartRepository;
import com.ra.security.UserPrincipal;
import com.ra.service.auth.IAuthService;
import com.ra.service.user.UserService;
import com.ra.service.user.shopping_cart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IAuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    public static Long getUserId() { // lay ra user_id dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getId();
    }

    @GetMapping("")
    public String home(Model model){
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(getUserId());
        double total = 0;
        for (ShoppingCart item: shoppingCartList) {
        total += item.getProduct().getPrice() * item.getQuantity();
        }
        long countById = shoppingCartRepository.count();
        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList", shoppingCartList);
        model.addAttribute("total", total);
        List<Product> productList =authService.getAll();
        model.addAttribute("productList",productList);
        List<Product> productListNewProduct =authService.getProductNewProduct(EnumDescriptionProduct.NewProduct);
        model.addAttribute("productListNewProduct",productListNewProduct);
        List<Product> productListNewProductFavoriteProduct = authService.getProductByFavoriteProduct(EnumDescriptionProduct.FavoriteProduct);
        model.addAttribute("productListNewProductFavoriteProduct",productListNewProductFavoriteProduct);
        return "user/index";
    }
}
