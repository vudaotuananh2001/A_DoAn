package com.ra.service.user;

import com.ra.models.entity.User;

import java.util.Optional;

public interface UserService {
   Optional<User> findByUserName(String userName);
}
