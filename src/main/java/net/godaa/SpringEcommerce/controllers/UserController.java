package net.godaa.SpringEcommerce.controllers;

import net.godaa.SpringEcommerce.config.BcryptGenerator;
import net.godaa.SpringEcommerce.models.User;
import net.godaa.SpringEcommerce.responses.ApiResponse;
import net.godaa.SpringEcommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static net.godaa.SpringEcommerce.responses.ResponseStatus.getValidResponseStatus;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    BcryptGenerator bcryptGenerator;


    @ResponseBody
    @PostMapping("/ahmed")
    public ApiResponse<?> registerUser(@Valid @RequestBody User user) {
        logger.info("Registering User");
        User userEmail = userService.findUserByEmail(user.getEmail());
        User userMobile = userService.findUserByMobile(user.getMobile());
        User userPassword = userService.findUserPassword(user.getPassword());
        if (userEmail != null) {
            return new ApiResponse<>("\"There is already a user registered with the email provided\"", getValidResponseStatus(HttpStatus.OK));

        } else if (userMobile != null) {
            return new ApiResponse<>("\"There is already a user registered with the mobile provided\"", getValidResponseStatus(HttpStatus.OK));
        } else if (userPassword != null) {
            return new ApiResponse<>("\"User password required\"");

        } else {
            //   User Not Exist
            String password = bcryptGenerator.passwordEncoder(user.getPassword());
            user.setPassword(password);
            userService.save(user);
            logger.info("done Created the user ");
            return new ApiResponse<>("\"User details successfully saved in the database\"");
        }
    }

//    @PostMapping("user/login")
//    @ResponseBody
//    public ApiResponse<?> loginUser(@Valid @RequestBody User user) {
//        logger.info("SigningIn User ");
//        User userEmail = userService.findUserByEmail(user.getEmail());
//        User userMobile = userService.findUserByMobile(user.getMobile());
//        User userPassword = userService.findUserPassword(user.getPassword());
//        String currentPassword = user.getPassword();
//
//
//    }


}
