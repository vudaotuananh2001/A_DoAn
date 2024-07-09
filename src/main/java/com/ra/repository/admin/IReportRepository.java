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
    @Query("SELECT o FROM Order o WHERE (o.sentDate BETWEEN :sentDateStart AND :sentDateEnd OR o.receivedate BETWEEN :receivedDateStart AND :receivedDateEnd)" +
            " AND (o.statusEnum = :statusConfirm OR o.statusEnum = :statusPaid)")
    Page<Order> getCombinedReport(
            @Param("sentDateStart") Date sentDateStart,
            @Param("sentDateEnd") Date sentDateEnd,
            @Param("receivedDateStart") Date receivedDateStart,
            @Param("receivedDateEnd") Date receivedDateEnd,
            @Param("statusConfirm") OrderStatusEnum statusConfirm,
            @Param("statusPaid") OrderStatusEnum statusPaid,
            Pageable pageable);


    @Query("select o from  Order o where o.statusEnum =:status or o.statusEnum=:statusPaid")
    Page<Order> getAllReportConfirm(Pageable pageable,
            @Param("status")OrderStatusEnum statusEnum,
            @Param("statusPaid")OrderStatusEnum statusPaid
    );

}
