package net.godaa.SpringEcommerce.services;

import net.godaa.SpringEcommerce.models.User;

public interface UserDao {

    User findUserByEmail(String email);

    User findUserByMobile(String mobile);

    User findUserPassword(String password);

    void save(User user);

//    void updateUser(User user);

    User findUserById(Long userId);


}
