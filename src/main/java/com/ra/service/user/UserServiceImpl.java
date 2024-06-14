package com.ra.service.user;

import com.ra.models.entity.Role;
import com.ra.models.entity.RoleEnum;
import com.ra.repository.user.RoleRepository;
import com.ra.repository.user.UserRepository;
import com.ra.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        if(userRepository.existsByUserName(user.getUserName())){
            throw  new RuntimeException("Tài khoản đã tồn tại");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByRoleName(RoleEnum.USER));
        User newUser =  new User();
        newUser.setFullName(user.getFullName());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setAddress(user.getAddress());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setRoles(roles);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
