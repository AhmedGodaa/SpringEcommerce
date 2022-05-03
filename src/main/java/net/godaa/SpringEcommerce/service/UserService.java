package net.godaa.SpringEcommerce.service;

import net.godaa.SpringEcommerce.domain.User;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    User findOrCreateUser(String name, String surname,
                                String phone, String email,
                                String address);
    User findByEmailAndPassword(String email, String password);

    void setPassword(Integer userId, String password);
}
