package com.ra.controller.user;

import com.ra.models.dto.request.WishListRequest;
import com.ra.models.entity.ShoppingCart;
import com.ra.models.entity.WishList;
import com.ra.repository.user.ShoppingCartRepository;
import com.ra.security.UserPrincipal;
import com.ra.service.user.shopping_cart.ShoppingCartService;
import com.ra.service.user.wishlist.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/wishlist")
public class WishListController {
    @Autowired
    private IWishListService iWishListService;
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
    public String home_wishList(Model model){
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(getUserId());
        double total = 0;
        for (ShoppingCart item: shoppingCartList) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        long countById = shoppingCartRepository.count();
        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList", shoppingCartList);
        model.addAttribute("total", total);
        List<WishList> wishListList = iWishListService.getAll(getUserId());
        model.addAttribute("wishListList",wishListList);
        return "/user/wishlist";
    }

    @GetMapping("/add/{id}")
    public String add_wishList (@PathVariable("id") Long id){
        WishListRequest wishListRequest =  new WishListRequest();
        wishListRequest.setProductId(id);
        wishListRequest.setUserId(getUserId());
        iWishListService.add(wishListRequest,getUserId());
        return "redirect:/user";
    }


    @GetMapping("/delete/{id}")
    public String delete_WishList(@PathVariable("id") Long id){
        iWishListService.deleteById(id);
        return "redirect:/user/wishlist";
    }
}
