package com.ra.controller.user;

import com.ra.models.entity.Order;
import com.ra.models.entity.Product;
import com.ra.models.entity.ShoppingCart;
import com.ra.models.entity.User;
import com.ra.security.UserPrincipal;
import com.ra.service.user.UserService;
import com.ra.service.user.order.OrderService;
import com.ra.service.user.orderdetail.OrderDetailService;
import com.ra.service.user.shopping_cart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/checkout")
public class CheckOutController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    public static Long getUserId() { // lay ra user_id dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getId();
    }

    @GetMapping("")
    public String homeCheckOut(Model model){
        Long userId = getUserId();
        User user = userService.findById(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(userId);
        double total = 0;
        for(ShoppingCart iteam :shoppingCartList){
            total += iteam.getProduct().getPrice() * iteam.getQuantity();
        }
        model.addAttribute("user",user);
        model.addAttribute("total",total);
        model.addAttribute("shoppingCartList",shoppingCartList);
        return  "user/order";
    }

    @PostMapping("/checkout")
    public String checkOut(){
        User user    = userService.findById(getUserId());
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(getUserId());
        double total=0;
        for(ShoppingCart iteam : shoppingCartList){
            total += iteam.getProduct().getPrice() * iteam.getQuantity();
        }
        Order order =orderService.add(user,total);
        // tạo mới orderdatails
        for (ShoppingCart shopingCart: shoppingCartList) {
            int orderQuantity = shopingCart.getQuantity();
            Product product = shopingCart.getProduct();
            orderDetailService.add(product, order, orderQuantity);
        }
       shoppingCartList.forEach(shoppingCart -> shoppingCartService.deleteById(shoppingCart.getId()));
        return "redirect:/user";
    }
}
