package com.ra.controller.admin;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import com.ra.models.entity.Product;
import com.ra.service.admin.order.IOrderServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Autowired
    private IOrderServiceAdmin iOrderServiceAdmin;

    @GetMapping("")
    public String home(Model model,
                       @RequestParam(name = "search", required = false) String search,
                       @RequestParam(name = "pageNo", defaultValue = "1") Integer page) {
        Page<Order> orderList = iOrderServiceAdmin.getAllOrderPage(page, search);
        model.addAttribute("totalPage", orderList.getTotalPages());
        model.addAttribute("currenPage", page);
        model.addAttribute("orderList", orderList);
        return "admin/order/order";
    }
}
