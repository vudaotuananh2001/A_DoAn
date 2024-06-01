package com.ra.controller.user;

import com.ra.models.entity.Order;
import com.ra.models.entity.ShoppingCart;
import com.ra.models.entity.User;
import com.ra.repository.user.OrderRepository;
import com.ra.security.UserPrincipal;
import com.ra.service.admin.order.IOrderServiceAdmin;
import com.ra.service.user.UserService;
import com.ra.service.user.order.OrderService;
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
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService  orderService;
    public static Long getUserId() { // lay ra user_id dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getId();
    }
    @GetMapping("")
    public String home(Model model){
        Long userId = getUserId();
        User user = userService.findById(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(userId);
        double total = 0;
        for (ShoppingCart item: shoppingCartList) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        model.addAttribute("shoppingCartList", shoppingCartList);
        model.addAttribute("user", user);
        model.addAttribute("total", total);
        return "user/order";
    }

    @GetMapping("/list")
    public String list (Model model){
        List<Order> listOrder =orderService.getAllOrder(getUserId());
        model.addAttribute("listOrder",listOrder);
        return "user/listorder";
    }

}
