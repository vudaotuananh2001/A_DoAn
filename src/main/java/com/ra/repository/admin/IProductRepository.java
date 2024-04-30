package com.ra.repository.admin;

import com.ra.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
  //  List<Product> findProductByProductNameLike(String search);
    Page<Product> findProductByProductNameLike(String search, Pageable pageable);

}
