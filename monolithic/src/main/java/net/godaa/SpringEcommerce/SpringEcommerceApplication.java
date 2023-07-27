package net.godaa.SpringEcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Profile;


@Profile({"development", "production", "testing"})

@SpringBootApplication
@EnableFeignClients
public class SpringEcommerceApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringEcommerceApplication.class, args);


         
        

    }


//    @Override
//    public void run(String... args) throws Exception {
//        fileService.deleteAll();
//        fileService.init();
//
//    }
//


//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
////
//            userService.saveRole(new Role(null, ROLE_USER));
//            userService.saveRole(new Role(null, ROLE_ADMIN));
//
//            userService.saveUser(new User(null, "ahmed", "ahmed@gmail.com", "password", new ArrayList<>()));
//            userService.saveUser(new User(null, "ali", "ali@gmail.com", "password", new ArrayList<>()));
//
//            userService.addRoleToUser("ahmed", ROLE_ADMIN);
//        };
//
//    }


}
