package com.ra.security;

import com.ra.repository.user.UserRepository;
import com.ra.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUserName(username);
       if(optionalUser.isPresent()){
           User user = optionalUser.get();
           return  UserPrincipal.builder()
                   .user(user)
                   .authorities(user.getRoles().stream().map(iteam ->
                           new SimpleGrantedAuthority(iteam.getRoleName().name()))
                           .collect(Collectors.toSet()))
                   .build();
       }
        throw new RuntimeException("role not found");
    }
}
