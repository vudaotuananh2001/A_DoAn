package com.ra.controller.auth;

import com.ra.models.entity.Product;
import com.ra.service.admin.product.IProductService;
import com.ra.service.auth.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("")
public class AuthController {
    @Autowired
    private IAuthService authService;
    @Autowired
    private IProductService productService;
    @GetMapping("")
    public String index(Model model){
        List<Product> productList =authService.getAll();
        model.addAttribute("productList",productList);
        return "index";
    }

    @GetMapping("/auth/details/{id}")
    public String details(@PathVariable("id") Long id,
                          Model model){
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "details";
    }



}
