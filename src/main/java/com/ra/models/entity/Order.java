package com.ra.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double orderPrice;
    private String note;
    private Date sentDate; // ngày gửi
    private Date receivedate; // ngày nhận

    @ManyToOne // người nhận
    @JoinColumn(name = "userid",referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    List<OrderDetails> orderDetails;
}
