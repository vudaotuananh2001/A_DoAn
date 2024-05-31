package com.ra.controller.admin;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import com.ra.service.admin.order.IOrderServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Autowired
    private IOrderServiceAdmin iOrderServiceAdmin;

    @GetMapping("")
    public String order(Model model){
        List<Order> orderList = iOrderServiceAdmin.getAll(OrderStatusEnum.WAITTING);
        model.addAttribute("orderList",orderList);
        return  "admin/order/order";
    }
}
