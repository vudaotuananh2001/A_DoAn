package com.ra.models.dto.repone;

import lombok.*;

@Getter
@Setter
@Builder
public class ProductOrderDto {
    private Long productId;
    private String productName;
    private Long totalQuantity;

    // Constructors
    public ProductOrderDto(Long productId, String productName, Long totalQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
    }
}
