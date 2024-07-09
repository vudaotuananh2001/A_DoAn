package com.ra.repository.user;

import com.ra.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUserName(String userName);
    Optional<User> findById(Long id);
    boolean existsByUserName(String Username);
    Page<User> findUserByPhone(String seachPhone, Pageable pageNo);
}
