package com.ra.service.admin.order;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import com.ra.repository.user.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImplAdmin implements  IOrderServiceAdmin{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Order> getAll(OrderStatusEnum statusEnum) {
        return orderRepository.findOrdersByStatusEnum(statusEnum);
    }
}
