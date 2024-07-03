package com.ra.service.user.orderdetail;

import com.ra.models.dto.repone.ProductOrderDto;
import com.ra.models.entity.Order;
import com.ra.models.entity.OrderDetails;
import com.ra.models.entity.Product;
import com.ra.repository.user.OrderDatailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements  OrderDetailService{

    @Autowired
    private OrderDatailRepository orderDatailRepository;
    @Override
    public List<OrderDetails> getAll(Long id) {
        return orderDatailRepository.findOrderDetailsByOrderId(id);
    }

    @Override
    public List<ProductOrderDto> top20Product() {
        return orderDatailRepository.findTop20Products();
    }

    @Override
    public OrderDetails add(Product product, Order order, int quantity) {
        OrderDetails orderDetails = OrderDetails.builder()
                .product(product)
                .order(order)
                .price(product.getPrice())
                .quantity(quantity)
                .build();
        return orderDatailRepository.save(orderDetails);
    }

}
