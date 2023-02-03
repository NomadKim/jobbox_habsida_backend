package com.example.jobbox.repository;

import com.example.jobbox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query(value = "SELECT user FROM User user, IN (user.roles) role where role = 'ROLE_USER'")
    List<User> getAllUsersExcludeAdmin();

//    List<User> getUsersByRolesContains(String role);
}
