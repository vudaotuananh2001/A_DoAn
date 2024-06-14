package com.ra.controller.user;

import com.ra.models.entity.Order;
import com.ra.models.entity.ShoppingCart;
import com.ra.models.entity.User;
import com.ra.repository.user.ShoppingCartRepository;
import com.ra.security.UserPrincipal;
import com.ra.service.user.UserService;
import com.ra.service.user.order.OrderService;
import com.ra.service.user.shopping_cart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/account")
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private OrderService orderService;
    public static Long getUserId() { // lay ra user_id dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getId();
    }
    @GetMapping("")
    public String account(Model model){
        User user= userService.findById(getUserId());
        List<ShoppingCart> shoppingCartList  = shoppingCartService.getAll(getUserId());
        double total=0;
        for(ShoppingCart iteam : shoppingCartList){
            total += iteam.getProduct().getPrice() * iteam.getQuantity();
        }
        long countById = shoppingCartRepository.count();
        List<Order> orderList= orderService.getAllOrder(getUserId());
        model.addAttribute("orderList",orderList);
        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList",shoppingCartList);
        model.addAttribute("total",total);
        model.addAttribute("user",user);
        return  "/user/account";
    }
    @PostMapping("")
    public String updateAccount(@ModelAttribute("user") User user){
        User userUpdate = userService.findById(getUserId());
        if(userUpdate!=null){
            userUpdate.setFullName(user.getFullName());
            userUpdate.setAddress(user.getAddress());
            userUpdate.setEmail(user.getEmail());
            userUpdate.setPhone(user.getPhone());
            userService.save(userUpdate);
        }
        return  "redirect:/user/account";
    }

}
