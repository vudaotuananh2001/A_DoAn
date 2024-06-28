package com.ra.controller;

import com.ra.config.PaypalPaymentIntent;
import com.ra.config.PaypalPaymentMethod;
import com.ra.models.entity.Order;
import com.ra.models.entity.Product;
import com.ra.models.entity.ShoppingCart;
import com.ra.models.entity.User;
import com.ra.security.UserPrincipal;
import com.ra.service.PaypalService;
import com.ra.service.user.UserService;
import com.ra.service.user.order.OrderService;
import com.ra.service.user.orderdetail.OrderDetailService;
import com.ra.service.user.shopping_cart.ShoppingCartService;
import com.ra.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.util.List;

@Controller
public class PaymentController{
    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;
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
    @GetMapping("/pay/test")
    public String index(Model model){
        Long userId = getUserId();
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(userId);
        double total = 0;
        for(ShoppingCart iteam :shoppingCartList){
            total += iteam.getProduct().getPrice() * iteam.getQuantity();
        }
        model.addAttribute("total",total);
        return "paypal/index";
    }

    @PostMapping("/pay")
    public String pay(HttpServletRequest request, @RequestParam("price") double price )
    {
        String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        try{
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks())
                if(links.getRel().equals("approval_url"))
                    return "redirect:" + links.getHref();
        }
		 catch (PayPalRESTException e){
             log.error(e.getMessage());
         }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "paypal/cancel";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId)
    {
        try{
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                User user    = userService.findById(getUserId());
                List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(getUserId());
                double total=0;
                for(ShoppingCart iteam : shoppingCartList){
                    total += iteam.getProduct().getPrice() * iteam.getQuantity();
                }
                Order order =orderService.addPaid(user,total);
                // tạo mới orderdatails
                for (ShoppingCart shopingCart: shoppingCartList) {
                    int orderQuantity = shopingCart.getQuantity();
                    Product product = shopingCart.getProduct();
                    orderDetailService.add(product, order, orderQuantity);
                }
                shoppingCartList.forEach(shoppingCart -> shoppingCartService.deleteById(shoppingCart.getId()));
            }
                return "paypal/success";
        }
        catch (PayPalRESTException e){
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

}





