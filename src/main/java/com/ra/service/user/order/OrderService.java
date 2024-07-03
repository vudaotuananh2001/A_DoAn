package com.ra.service.user.order;

import com.ra.models.entity.Order;
import com.ra.models.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Order add (User user, Double total);
    Order addPaid (User user, Double total);
    Page<Order> getAllOrder(Long id,Integer pageNo);
    Order save (Order order);
    Order getByIdOrder (Long id);
}
