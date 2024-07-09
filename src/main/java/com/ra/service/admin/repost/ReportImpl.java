package com.ra.service.admin.repost;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import com.ra.repository.admin.IReportRepository;
import com.ra.repository.user.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReportImpl implements IReport{
    @Autowired
    private IReportRepository iReportRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Page<Order> getOrderTo(Integer pageNo, Date sentDate, Date receivedDate) {
        Pageable pageable = PageRequest.of(pageNo-1,10);
        OrderStatusEnum statusEnum = OrderStatusEnum.CONFIRM;
        OrderStatusEnum statusPaid = OrderStatusEnum.PAID;
        if (sentDate != null && receivedDate != null) {
            return iReportRepository.getCombinedReport(sentDate, sentDate, receivedDate, receivedDate, statusEnum, statusPaid, pageable);
        }
        return iReportRepository.getAllReportConfirm(pageable,statusEnum,statusPaid);
    }
}
