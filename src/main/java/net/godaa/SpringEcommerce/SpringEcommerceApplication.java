package net.godaa.SpringEcommerce;

import net.godaa.SpringEcommerce.domain.Cart;
import net.godaa.SpringEcommerce.domain.Role;
import net.godaa.SpringEcommerce.repository.CartRepo;
import net.godaa.SpringEcommerce.repository.ProductRepo;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.service.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication()
public class SpringEcommerceApplication {
    ProductRepo productRepo;
    CartRepo cartRepo;

    public static void main(String[] args) {
        SpringApplication.run(SpringEcommerceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserServiceImpl userService) {
        return args -> {

            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveUser(new User(null, "ahmed", "ahmed@gmail.com", "password", new ArrayList<>(), new Cart()));
            Cart cart = new Cart();
            userService.addRoleToUser("ahmed", SecurityConstants.ROLE_ADMIN);
            cartRepo.save(new Cart());
        };

    }


}
