package com.ra.service.admin.order;

import com.ra.models.dto.repone.UserDTO;
import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import com.ra.models.entity.Product;
import com.ra.repository.user.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImplAdmin implements  IOrderServiceAdmin{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> getAllOrderPage(Integer pageNo, String search) {
        Pageable pageable = PageRequest.of(pageNo-1,7);
        if(search!=null && !search.isEmpty()){
            return  orderRepository.findOrdersByUser_FullNameLike("%" +search+"%",pageable);
        }
        return orderRepository.findAll(pageable);
    }

        @Override
        public Page<UserDTO> getTotalOrderPriceByCustomer(Integer pageNo) {
            Pageable pageable = PageRequest.of(pageNo-1,10);
           return orderRepository.findTotalOrderPriceByCustomer(pageable);
        }
}
