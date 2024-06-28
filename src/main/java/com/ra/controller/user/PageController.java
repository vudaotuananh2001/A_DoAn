package com.ra.controller.user;

import com.ra.models.entity.ShoppingCart;
import com.ra.repository.user.ShoppingCartRepository;
import com.ra.security.UserPrincipal;
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
@RequestMapping("/user/page")
public class PageController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    public static Long getUserId() { // lay ra user_id dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getId();
    }

    @GetMapping("")
    public  String homePage(Model model){
        long userId = getUserId();
        System.err.println(userId);
        List<ShoppingCart> shoppingCartList  = shoppingCartService.getAll(userId);
        double total=0;
        for(ShoppingCart iteam : shoppingCartList){
            total += iteam.getProduct().getPrice() * iteam.getQuantity();
        }
        long countById = shoppingCartRepository.count();
        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList",shoppingCartList);
        model.addAttribute("total",total);
        return "user/page";
    }
}
