package com.ra.service.admin.repost;

import com.ra.models.entity.Order;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface IReport {
    Page<Order> getOrderTo(Integer pageNo, Date sentDate, Date receivedDate);
}
