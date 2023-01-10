package com.shift.tracking.repository;

import com.shift.tracking.entity.Role;
import com.shift.tracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAllByRolesNotContaining(Role role);
}
