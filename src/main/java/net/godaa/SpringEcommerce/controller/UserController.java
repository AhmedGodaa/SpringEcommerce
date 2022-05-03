package net.godaa.SpringEcommerce.controller;

import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.repository.UserRepo;
import net.godaa.SpringEcommerce.utils.FileUploadUtil;
import net.godaa.SpringEcommerce.domain.Role;
import net.godaa.SpringEcommerce.domain.RoleForm;
import net.godaa.SpringEcommerce.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

import static net.godaa.SpringEcommerce.security.jwt.SecurityConstants.ROLE_USER;


@Controller
@RequestMapping("/api")
public class UserController {


    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userRepo.findAll());
    }


    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestPart("user") User user, @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename()).toLowerCase().replaceAll(" ", "_");
        user.setImage(fileName);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepo.findByName(ROLE_USER));
        User savedUser = userRepo.save(user);
        String uploadDir = "user-photos/" + user.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }



    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return new ResponseEntity<>(roleRepo.save(role), HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = userRepo.findById(id);
        if (!userOptional.isPresent())
            return ResponseEntity.notFound().build();
        user.setId(id);
        return ResponseEntity.ok(userRepo.save(user));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        try {
            userRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleForm role) {
        if (role.getUsername() != null && role.getRole() != null) {
            Role _role = roleRepo.findByName(role.getRole());
            User user = userRepo.findByUsername(role.getUsername());
            user.getRoles().add(_role);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("username or role is null");
        }


    }


}




