package com.ra.repository.user;

import com.ra.models.entity.ShoppingCart;
import com.ra.models.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository  extends JpaRepository<WishList,Long> {
    @Query("select s from  WishList s where s.users.id = :id")
    List<WishList> findByUser(@Param("id") Long id);
}
