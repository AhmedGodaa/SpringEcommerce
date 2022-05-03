package net.godaa.SpringEcommerce.service.impl;

import net.godaa.SpringEcommerce.domain.Role;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.repository.RoleRepo;
import net.godaa.SpringEcommerce.repository.UserRepo;
import net.godaa.SpringEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.godaa.SpringEcommerce.security.jwt.SecurityConstants.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }


    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepo.findByName(ROLE_USER));
        return userRepo.save(user);
    }


    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }


    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);


    }

    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User findOrCreateUser(String name, String surname, String phone, String email, String address) {
        return null;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return null;
    }

    @Override
    public void setPassword(Integer userId, String password) {

    }
}

