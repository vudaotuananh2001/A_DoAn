package com.ra.service.user.order;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import com.ra.models.entity.User;
import com.ra.repository.user.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements  OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order add(User user, Double total) {
            Order order = Order.builder()
                    .user(user)
                    .orderPrice(total)
                    .statusEnum(OrderStatusEnum.WAITTING)
                    .sentDate(new java.sql.Date(new java.util.Date().getTime()))
                    .build();
        return orderRepository.save(order);
    }
}
