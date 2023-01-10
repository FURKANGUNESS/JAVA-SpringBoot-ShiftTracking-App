package com.shift.tracking.service.impl;

import com.shift.tracking.entity.Role;
import com.shift.tracking.entity.User;
import com.shift.tracking.exception.WebServiceException;
import com.shift.tracking.repository.RoleRepository;
import com.shift.tracking.repository.UserRepository;
import com.shift.tracking.request.RegisterForm;
import com.shift.tracking.service.UserService;
import com.shift.tracking.util.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByEmail(String email) {
        //email e gore kullaniciyi arar
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(RegisterForm registration) {
        if (userRepository.existsByEmail(registration.getEmail())) {
            //kayit olmaya calisilan email, db de kayitliysa hata verir.
            throw new WebServiceException("There is an account with that email");
        }

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = new Role("ROLE_USER");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(roles);
        user.setDepartment(registration.getDepartment());

        //Kayit bilgileri User entity nesnesine aktarilarak db ye kaydedilir.
        userRepository.save(user);
    }

    @Override
    public boolean isAdminUser() {
        //Sessiondaki kullanıcının rollerinin arasında "ROLE_ADMIN" olup olmadığına bakar, varsa bu kullanıcı ADMIN' dir.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return roles.contains("ROLE_ADMIN");
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        Role role = roleRepository.findByName("ROLE_ADMIN");

        if (role == null) {
            return userRepository.findAll();
        }
        return userRepository.findAllByRolesNotContaining(role);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Giris islemleri sirasinda kullanilan method
        User user = userRepository.findByEmail(email);
        if (user == null) {
            //bu maile sahip bir kullanici yok ise giris yapmamasi icin hata verilir.
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
