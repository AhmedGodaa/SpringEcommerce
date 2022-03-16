package net.godaa.SpringEcommerce.services;

import net.godaa.SpringEcommerce.exceptions.NotFoundException;
import net.godaa.SpringEcommerce.models.User;
import net.godaa.SpringEcommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    @Override
    public User findUserPassword(String password) {
        return userRepository.findByPassword(password);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

//    @Override
//    public void updateUser(User user) {
//
//        Long id = user.getId();
//
//        User editUser = userRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("User", "id", id));
//
//        if (editUser.getDate() != null) {
//            editUser.setDate(user.getDate());
//        }
//        if (editUser.getFirstName() != null) {
//            editUser.setFirstName(user.getFirstName());
//
//        }
//        userRepository.save(editUser);
//
//
//    }

    @Override
    public User findUserById(Long userId) {
        //        new ResourceNotFoundException   returns ->  "User" + not found with "id" : "userId"
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "id", userId));
    }



}
