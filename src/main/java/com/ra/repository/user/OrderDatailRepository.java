package com.ra.repository.user;

import com.ra.models.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDatailRepository extends JpaRepository<OrderDetails,Long> {
    List<OrderDetails> findOrderDetailsByOrderId(Long id);
}
