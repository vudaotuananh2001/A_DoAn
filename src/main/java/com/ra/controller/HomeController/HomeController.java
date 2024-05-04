package com.ra.controller.HomeController;

import com.ra.models.entity.EnumDescriptionProduct;
import com.ra.models.entity.Product;
import com.ra.models.entity.User;
import com.ra.service.auth.IAuthService;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private IAuthService authService;
    @Autowired
    private UserService userService;
    @GetMapping("")
    public String index(Model model){
        List<Product> productList =authService.getAll();
        model.addAttribute("productList",productList);
        List<Product> productListNewProduct =authService.getProductNewProduct(EnumDescriptionProduct.NewProduct);
        model.addAttribute("productListNewProduct",productListNewProduct);
        List<Product> productListNewProductFavoriteProduct = authService.getProductByFavoriteProduct(EnumDescriptionProduct.FavoriteProduct);
        model.addAttribute("productListNewProductFavoriteProduct",productListNewProductFavoriteProduct);
        return "index";
    }
    
    @GetMapping("/login")
    public String login(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user){
            userService.register(user);
        return "login";
    }

}
