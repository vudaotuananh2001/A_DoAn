package com.ra.repository.admin;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface IReportRepository extends JpaRepository<Order,Long> {
    @Query("select  o from  Order  o where o.receivedate between  :receiveDateStart AND :receiveDateEnd ")
    Page<Order> getReport(@Param("receiveDateStart") Date receiveDateStart,
                          @Param("receiveDateEnd") Date receiveDateEnd,
                             Pageable pageable
    );

    @Query("select o from  Order o where o.statusEnum =:status")
    Page<Order> getAllReportConfirm(Pageable pageable, @Param("status")OrderStatusEnum statusEnum);
}
