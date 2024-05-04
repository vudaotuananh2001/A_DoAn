package com.ra.repository.user;

import com.ra.models.entity.Role;
import com.ra.models.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByRoleName(RoleEnum roleEnum);
}
