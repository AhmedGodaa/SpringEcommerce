package net.godaa.SpringEcommerce.controller;

import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.exceptions.EmailAlreadyUsedException;
import net.godaa.SpringEcommerce.exceptions.UsernameAlreadyUsedException;
import net.godaa.SpringEcommerce.repository.UserRepo;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;
import net.godaa.SpringEcommerce.service.UserService;
import net.godaa.SpringEcommerce.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;


    @GetMapping("/authorities")
    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }


    @PostMapping("/admin/users")
    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO, @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        log.debug("REST request to save User : {}", userDTO);
//        The (ID,Username,Email) Must Be Unique.

        if (userDTO.getId() != null) {
            throw new EmailAlreadyUsedException();
        } else if (userRepo.findByUsername(userDTO.getUsername().toLowerCase()).isPresent()) {
            throw new UsernameAlreadyUsedException();

        } else if (userRepo.findByUsername(userDTO.getUsername().toLowerCase()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User user = userService.createUser(userDTO);
            try {
                userService.addImageToUser(userDTO.getUsername(), multipartFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // TODO: Add Mail Service
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @PutMapping("/admin/users")
    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> user = userRepo.findByEmail(userDTO.getEmail());
//        if exist &&  user updated email,  and his id != old id
//        email already in use =   email belong to another account
        if (user.isPresent() && (!user.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        user = userRepo.findByUsername(userDTO.getUsername());
        if (user.isPresent() && (!user.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);
        return ResponseEntity.ok().body(updatedUser.orElseThrow(ResourceNotFoundException::new));
    }

    @DeleteMapping("/users/{login}")
    @PreAuthorize("hasAuthority(\"" + SecurityConstants.ROLE_ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable @Pattern(regexp = SecurityConstants.LOGIN_REGEX) String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/users/{username}")
    public ResponseEntity<HttpStatus> setUserImage(@RequestPart(value = "username") String username, @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        userService.addImageToUser(username, multipartFile);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/admin/users")
    @PreAuthorize("hasAuthority(\"" + SecurityConstants.ROLE_ADMIN + "\")")
    public ResponseEntity<List<User>> getAllUsers() {
        log.debug("REST request to get all User for an admin even not Activated");
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/admin/users/{id}")
    @PreAuthorize("hasAuthority(\"" + SecurityConstants.ROLE_ADMIN + "\")")
    public ResponseEntity<UserDTO> getUser(@PathVariable @Pattern(regexp = SecurityConstants.LOGIN_REGEX) String username) {
        log.debug("REST request to get User : {}", username);
        Optional<UserDTO> user = userService.getUserWithAuthoritiesByUsername(username).map(UserDTO::new);
        return ResponseEntity.ok(user.orElseThrow(ResourceNotFoundException::new));
    }


}




