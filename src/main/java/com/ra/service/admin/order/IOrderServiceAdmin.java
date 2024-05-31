package com.ra.service.admin.order;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;

import java.util.List;

public interface IOrderServiceAdmin {
    List<Order> getAll(OrderStatusEnum statusEnum);
}
