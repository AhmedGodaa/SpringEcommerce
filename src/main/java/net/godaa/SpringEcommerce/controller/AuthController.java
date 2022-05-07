package net.godaa.SpringEcommerce.controller;

import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserService userService;



    @PostMapping("/signup")
    public ResponseEntity<User> saveUser(@RequestPart(value = "user") User user, @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        if (user != null) {
            User userWithImage = userService.addImageToUser(user, multipartFile);
            User savedUser = userService.saveUser(userWithImage);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }
    }


}
