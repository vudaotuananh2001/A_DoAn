package com.ra.service.user.order;

import com.ra.models.entity.Order;
import com.ra.models.entity.User;

import java.util.List;

public interface OrderService {
    Order add (User user, Double total);
    List<Order> getAllOrder(Long id);
}
