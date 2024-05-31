package com.ra.service.user.wishlist;

import com.ra.models.dto.request.WishListRequest;
import com.ra.models.entity.Product;
import com.ra.models.entity.User;
import com.ra.models.entity.WishList;
import com.ra.repository.user.WishListRepository;
import com.ra.service.admin.product.IProductService;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WishListServiceImpl implements IWishListService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private IProductService productService;
    @Autowired
    private UserService userService;

    @Override
    public WishList add(WishListRequest wishListRequest, Long userId) {
        Product product  = productService.findById(wishListRequest.getProductId());
        User user= userService.findById(userId);
        WishList wishList=  WishList.builder()
                .product(product)
                .users(user)
                .build();
        return wishListRepository.save(wishList);
    }

    @Override
    public void deleteById(long id) {
        wishListRepository.deleteById(id);
    }

    @Override
    public List<WishList> getAll(Long id) {
        return wishListRepository.findByUser(id);
    }
}
