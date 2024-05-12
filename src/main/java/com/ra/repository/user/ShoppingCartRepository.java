package com.ra.repository.user;

import com.ra.models.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    @Query("select s from  ShoppingCart s where s.user.id = :id")
    List<ShoppingCart> findByUser(@Param("id") Long id);
//    @Query("select  count(count) from ShoppingCart")
//    long count();
}
