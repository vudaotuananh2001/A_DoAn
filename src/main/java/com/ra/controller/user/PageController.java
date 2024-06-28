package com.ra.controller.user;

import com.ra.models.entity.Category;
import com.ra.models.entity.Product;
import com.ra.models.entity.ShoppingCart;
import com.ra.repository.user.ShoppingCartRepository;
import com.ra.security.UserPrincipal;
import com.ra.service.admin.category.ICategoryService;
import com.ra.service.admin.product.IProductService;
import com.ra.service.user.shopping_cart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/page")
public class PageController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    public static Long getUserId() { // lay ra user_id dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getId();
    }
    @GetMapping("")
    public  String homePage(Model model,
                            @RequestParam(name = "pageNo", defaultValue = "1") Integer page){
        Long userId = getUserId();
        System.err.println(userId);
        List<ShoppingCart> shoppingCartList  = shoppingCartService.getAll(userId);
        double total=0;
        for(ShoppingCart iteam : shoppingCartList){
            total += iteam.getProduct().getPrice() * iteam.getQuantity();
        }
        long countById = shoppingCartRepository.countByUserId(getUserId());

        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList",shoppingCartList);
        model.addAttribute("total",total);

        Page<Product> listAllProduct = productService.getAllPageUser(page);
        model.addAttribute("totalPage", listAllProduct.getTotalPages());
        model.addAttribute("currenPage", page);
        model.addAttribute("listAllProduct", listAllProduct);

        List<Category> listAllCategory = categoryService.getAll();
        model.addAttribute("listAllCategory",listAllCategory);
        return "user/page";
    }

    @GetMapping("/category/{id}")
    public String cate(@PathVariable("id") Long id,
                       @RequestParam(name = "pageNo", defaultValue = "1") Integer page,
                       Model model){
        Page<Product>  listAllProduct = productService.getAllProductByCategoryId(id,page);
        model.addAttribute("listAllProduct",listAllProduct);
        model.addAttribute("totalPage", listAllProduct.getTotalPages());
        model.addAttribute("currenPage", page);

        List<Category> listAllCategory = categoryService.getAll();
        model.addAttribute("listAllCategory",listAllCategory);

        Long userId = getUserId();
        List<ShoppingCart> shoppingCartList  = shoppingCartService.getAll(userId);
        double total=0;
        for(ShoppingCart iteam : shoppingCartList){
            total += iteam.getProduct().getPrice() * iteam.getQuantity();
        }
        long countById = shoppingCartRepository.countByUserId(getUserId());
        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList",shoppingCartList);
        model.addAttribute("total",total);
        return "user/page";
    }
}
