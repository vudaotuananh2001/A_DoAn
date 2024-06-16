package com.ra.controller.admin;

import com.ra.models.entity.Order;
import com.ra.models.entity.ShoppingCart;
import com.ra.service.admin.order.IOrderServiceAdmin;
import com.ra.service.admin.repost.IReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/admin/report")
public class RepostController {
    @Autowired
    private IReport iReport;

    @GetMapping("")
    public String homeReport(
           Model model,
           @RequestParam(name = "sentDate", required = false)
           @DateTimeFormat(pattern = "yyyy-MM-dd") Date sentDate,
           @RequestParam(name = "receivedDate", required = false)
           @DateTimeFormat(pattern = "yyyy-MM-dd") Date receivedDate,
           @RequestParam(name = "pageNo", defaultValue = "1") Integer page
    ){
        Page<Order> listReport = iReport.getOrderTo(page,sentDate, receivedDate);
        double total=0;
        for(Order iteam : listReport){
            total += iteam.getOrderPrice();
        }
        model.addAttribute("listReport",listReport);
        model.addAttribute("total",total);
        model.addAttribute("totalPage", listReport.getTotalPages());
        model.addAttribute("currenPage", page);
        return "admin/repost/repost";
    }
}
