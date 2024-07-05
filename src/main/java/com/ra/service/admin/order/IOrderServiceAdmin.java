package com.ra.service.admin.order;

import com.ra.models.dto.repone.UserDTO;
import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import com.ra.models.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderServiceAdmin {
    List<Order> getAll();
    Page<Order> getAllOrderPage(Integer pageNo, String search);
    Page<UserDTO> getTotalOrderPriceByCustomer(Integer pageNo);
}
