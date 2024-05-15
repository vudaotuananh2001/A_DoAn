package com.ra.service.user.order;

import com.ra.models.entity.Order;
import com.ra.models.entity.ShoppingCart;
import com.ra.models.entity.User;

public interface OrderService {
    Order add (User user, Double total);

}
