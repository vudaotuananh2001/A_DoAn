package com.ra.service.user.order;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import com.ra.models.entity.User;
import com.ra.repository.user.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public Order addPaid(User user, Double total) {
        Order order = Order.builder()
                .user(user)
                .orderPrice(total)
                .statusEnum(OrderStatusEnum.PAID)
                .sentDate(new java.sql.Date(new java.util.Date().getTime()))
                .build();
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllOrder(Long id,Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,10);
        return orderRepository.findOrdersByUserId(id,pageable);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getByIdOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
