package com.shift.tracking.service;

import com.shift.tracking.entity.User;
import com.shift.tracking.request.RegisterForm;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    void save(RegisterForm registration);

    boolean isAdminUser();

    User getCurrentUser();

    List<User> getAllUsers();

    void save(User user);

    User findById(Long id);
}
