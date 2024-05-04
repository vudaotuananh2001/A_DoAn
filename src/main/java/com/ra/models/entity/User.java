package com.ra.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String userName;
    private String password;
    private String fullName;
    private  String address;
    private String email;
    private  String phone;
    private Boolean status=true;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),// tham chiếu đến bảng user
            inverseJoinColumns = @JoinColumn(name = "role_id") // tham chiếu đến bản role
    )
    private Set<Role> roles;
}
