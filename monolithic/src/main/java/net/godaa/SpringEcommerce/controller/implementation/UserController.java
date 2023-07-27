package net.godaa.SpringEcommerce.controller.implementation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;
import net.godaa.SpringEcommerce.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserController {

    @GetMapping("/account")
    ResponseEntity<User> getCurrentUser();

    @GetMapping("/{username}")
    ResponseEntity<User> getUserByUsername(@PathVariable("username") String username);




    @GetMapping("/get/{username}")
     ResponseEntity<UserDTO> getUser(@PathVariable @Pattern(regexp = SecurityConstants.LOGIN_REGEX) String username);


    @GetMapping("/sort/{property}/{order}")
    ResponseEntity<List<User>> getAllSorted(@PathVariable("property") String property, @PathVariable("order") String order);

    @GetMapping("/{elementPerPage}/{pageNumber}")
    ResponseEntity<Page<User>> getUserByPage(@PathVariable("elementPerPage") Integer elementPerPage, @PathVariable("pageNumber") Integer pageNumber);


    @PutMapping("/update")
    ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO);


    @DeleteMapping("/delete/{username}")
    ResponseEntity<Void> deleteUser(@PathVariable @Pattern(regexp = SecurityConstants.LOGIN_REGEX) String username);

    @PostMapping("/image")
    ResponseEntity<?> setUserImage(@RequestPart(value = "username") String username, @RequestPart(value = "image") MultipartFile multipartFile)throws IOException;

    @GetMapping("/getAll")
    ResponseEntity<List<User>> getAllUsers();
}
