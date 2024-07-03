package com.ra.repository.user;

import com.ra.models.dto.repone.ProductOrderDto;
import com.ra.models.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDatailRepository extends JpaRepository<OrderDetails,Long> {
    List<OrderDetails> findOrderDetailsByOrderId(Long id);
    @Query("SELECT new com.ra.models.dto.repone.ProductOrderDto(p.id, p.productName, SUM(od.quantity)) " +
            "FROM OrderDetails od " +
            "JOIN od.product p " +
            "GROUP BY p.id, p.productName " +
            "ORDER BY SUM(od.quantity) DESC")
    List<ProductOrderDto> findTop20Products();
}
