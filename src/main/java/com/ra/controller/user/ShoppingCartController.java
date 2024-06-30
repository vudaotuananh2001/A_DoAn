package com.ra.controller.user;

import com.ra.models.dto.request.ShoppingCartRequest;
import com.ra.models.entity.Product;
import com.ra.models.entity.ShoppingCart;
import com.ra.repository.user.ShoppingCartRepository;
import com.ra.security.UserPrincipal;
import com.ra.service.admin.product.IProductService;
import com.ra.service.user.shopping_cart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private IProductService productService;

    public static Long getUserId() { // lay ra user_id dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getId();
    }

    @GetMapping("")
    public String home(Model model){
        Long userId= getUserId();
        List<ShoppingCart> shoppingCartList  = shoppingCartService.getAll(userId);
        double total=0;
        for(ShoppingCart iteam : shoppingCartList){
            total += iteam.getProduct().getPrice() * iteam.getQuantity();
        }
        long countById = shoppingCartRepository.countByUserId(getUserId());
        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList",shoppingCartList);
        model.addAttribute("total",total);
        return "/user/cart";
    }

    @GetMapping("/add/{id}")
    public String addProductCart(@PathVariable("id") Long id){
        Long userId= getUserId();// id đăng nhập
            ShoppingCartRequest shoppingCartRequest = new ShoppingCartRequest();
            shoppingCartRequest.setProductId(id);
            shoppingCartRequest.setQuantity(1);
            shoppingCartService.add(shoppingCartRequest,userId);
        System.err.println(id);
        return "redirect:/user";
    }

    @GetMapping("/page/add/{id}")
    public String addProductCartPage(@PathVariable("id") Long id){
        Long userId= getUserId();// id đăng nhập
        ShoppingCartRequest shoppingCartRequest = new ShoppingCartRequest();
        shoppingCartRequest.setProductId(id);
        shoppingCartRequest.setQuantity(1);
        shoppingCartService.add(shoppingCartRequest,userId);
        return "redirect:/user/page";
    }

    @PostMapping("/cart/edit/{id}")
    @ResponseBody
    public ShoppingCart updateQuantity(@PathVariable("id") Long productId, @RequestParam("quantity") int quantity) {
        Long userId = getUserId();
        ShoppingCart shopingCart = shoppingCartService.findShoppingCartById(userId, productId);
        shopingCart.setQuantity(quantity);
        return shoppingCartService.save(shopingCart);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        ShoppingCart cartToDelete = shoppingCartRepository.findById(id).orElse(null); // Lấy thông tin của giỏ hàng
        if (cartToDelete != null) {
            Product product = cartToDelete.getProduct();
            int quantityToDelete = cartToDelete.getQuantity();

            // Cập nhật lại số lượng sản phẩm trong kho
            product.setQuantity(product.getQuantity() + quantityToDelete);
            productService.save(product);

            // Xóa sản phẩm khỏi giỏ hàng
            shoppingCartService.deleteById(id);
        }
        return "redirect:/user";
    }

    @GetMapping("/page/delete/{id}")
    public String deletePage(@PathVariable("id") Long id) {
        ShoppingCart cartToDelete = shoppingCartRepository.findById(id).orElse(null); // Lấy thông tin của giỏ hàng
        if (cartToDelete != null) {
            Product product = cartToDelete.getProduct();
            int quantityToDelete = cartToDelete.getQuantity();

            // Cập nhật lại số lượng sản phẩm trong kho
            product.setQuantity(product.getQuantity() + quantityToDelete);
            productService.save(product);

            // Xóa sản phẩm khỏi giỏ hàng
            shoppingCartService.deleteById(id);
        }
        return "redirect:/user/page";
    }


    @GetMapping("/cart/delete/{id}")
    public String deleteCart(@PathVariable("id") Long id){
        ShoppingCart cartToDelete = shoppingCartRepository.findById(id).orElse(null); // Lấy thông tin của giỏ hàng
        if (cartToDelete != null) {
            Product product = cartToDelete.getProduct();
            int quantityToDelete = cartToDelete.getQuantity();

            // Cập nhật lại số lượng sản phẩm trong kho
            product.setQuantity(product.getQuantity() + quantityToDelete);
            productService.save(product);

            // Xóa sản phẩm khỏi giỏ hàng
            shoppingCartService.deleteById(id);
        }
        return "redirect:/user/shopping-cart";
    }

}
