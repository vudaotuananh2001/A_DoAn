package com.ra.repository.user;

import com.ra.models.dto.repone.UserDTO;
import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findOrdersByUserId(Long id, Pageable pageable);
    Page<Order> findOrdersByUser_FullNameLike (String fullName, Pageable pageable);

    @Query("SELECT new com.ra.models.dto.repone.UserDTO(o.user.id, o.user.fullName, o.user.phone, o.user.email, SUM(o.orderPrice)) " +
            "FROM Order o " +
            "GROUP BY o.user.id, o.user.fullName, o.user.phone, o.user.email " +
            "ORDER BY SUM(o.orderPrice) DESC")
    Page<UserDTO> findTotalOrderPriceByCustomer(Pageable pageable);
}
