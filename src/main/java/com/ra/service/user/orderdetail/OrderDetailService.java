package com.ra.service.user.orderdetail;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderDetails;
import com.ra.models.entity.Product;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetails> getAll(Long id);
    OrderDetails add (Product product, Order order,int quantity);
}
