package com.ra.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    @Column(length = 300)
    @Enumerated(EnumType.STRING)
    private EnumDescriptionProduct description;
    @Column(length = 1000)
    private String detail;
    @Min(0)
    private Double price;
    private String image;
    @Min(0)
    private int quantity;
    private Boolean status=true;
    @ManyToOne
    @JoinColumn(name = "category_id" , referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<OrderDetails> orderDetails;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<ShoppingCart> shopingCarts;
}
