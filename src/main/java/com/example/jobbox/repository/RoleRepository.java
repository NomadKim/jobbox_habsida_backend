package com.example.jobbox.repository;

import com.example.jobbox.model.enums.ERole;
import com.example.jobbox.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
