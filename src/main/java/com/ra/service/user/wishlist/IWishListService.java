package com.ra.service.user.wishlist;

import com.ra.models.dto.request.WishListRequest;
import com.ra.models.entity.WishList;

import java.util.List;

public interface IWishListService {
    List<WishList> getAll(Long id);
    WishList add (WishListRequest wishListRequest, Long userId);
    void deleteById(long id);
}
