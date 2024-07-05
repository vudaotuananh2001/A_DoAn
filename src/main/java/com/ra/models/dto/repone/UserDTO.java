package com.ra.models.dto.repone;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName; // Tên người dùng
    private String phoneNumber;
    private String userEmail; // Email người dùng (hoặc thông tin khác nếu cần)
    private Double totalOrderPrice;
}
