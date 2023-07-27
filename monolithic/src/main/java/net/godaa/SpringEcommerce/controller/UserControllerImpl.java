package net.godaa.SpringEcommerce.controller;


import net.godaa.SpringEcommerce.controller.implementation.UserController;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.exceptions.AccountNotExistException;
import net.godaa.SpringEcommerce.repository.UserRepo;
import net.godaa.SpringEcommerce.service.UserService;
import net.godaa.SpringEcommerce.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final Logger log = LoggerFactory.getLogger(UserControllerImpl.class);


    UserRepo userRepo;

    UserService userService;


    public UserControllerImpl(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;

    }


    @Override
    public ResponseEntity<User> getCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return ResponseEntity.ok(userService.findByUsername(username).orElseThrow(AccountNotExistException::new));

    }

    @Override
    public ResponseEntity<User> getUserByUsername(String username) {
        return ResponseEntity.ok(userService.findByUsername(username).orElseThrow(AccountNotExistException::new));

    }

    public String print() {
        System.out.println("Google Chrome is the best in the world");
        return "ahmed";
    }


    @Override
    public ResponseEntity<UserDTO> getUser(String username) {
        log.debug("REST request to get User : {}", username);
        Optional<UserDTO> user = userService.getUserWithAuthoritiesByUsername(username).map(UserDTO::new);
        return ResponseEntity.ok(user.orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public ResponseEntity<List<User>> getAllSorted(String property, String order) {
        if (order.equals("asc")) {
            return ResponseEntity.ok().body(userRepo.findAll(Sort.by(Sort.Order.asc(property))));

        } else {
            return ResponseEntity.ok().body(userRepo.findAll(Sort.by(Sort.Order.desc(property))));

        }
    }

    @Override
    public ResponseEntity<Page<User>> getUserByPage(Integer elementPerPage, Integer pageNumber) {

        return ResponseEntity.ok().body(userRepo.findAll(PageRequest.of(pageNumber, elementPerPage)));

    }
//    UserDetailsImpl userDetails = (UserDetailsImpl) loggedInUser.getPrincipal();

    @Override
    public ResponseEntity<UserDTO> updateUser(UserDTO userDTO) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        Optional<User> userDetails = userService.findByUsername(username);
        if (userDetails.isPresent() && (!userDetails.get().getUsername().equals(userDTO.getUsername()))) {
            throw new AccountNotExistException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);
        return ResponseEntity.ok().body(updatedUser.orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public ResponseEntity<Void> deleteUser(String username) {
        log.debug("REST request to delete User: {}", username);
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> setUserImage(String username, MultipartFile multipartFile) throws IOException {
        userService.addImageToUser(username, multipartFile);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        log.debug("REST request to get all User for an admin even not Activated");
        return ResponseEntity.ok().body(userService.getAllUsers());
    }


}




