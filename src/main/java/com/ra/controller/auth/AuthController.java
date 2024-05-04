package com.ra.controller.auth;

import com.ra.models.entity.Product;
import com.ra.service.admin.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IProductService productService;
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id,
                          Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "details";
    }

}
