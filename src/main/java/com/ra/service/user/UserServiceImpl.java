package com.ra.service.user;

import com.ra.repository.user.UserRepository;
import com.ra.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }
}
