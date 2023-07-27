package net.godaa.SpringEcommerce.controller;

import jakarta.validation.Valid;
import net.godaa.SpringEcommerce.controller.implementation.TestService;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.service.dto.UserDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static net.godaa.SpringEcommerce.security.jwt.SecurityConstants.TOKEN_EXAMPLE;

@Controller
@RequestMapping("/cloud")
public class CloudRequests {

    RestTemplate restTemplate;

    FeignRequests feignRequests;
    TestService service;

    public CloudRequests(RestTemplate restTemplate, FeignRequests feignRequests, TestService service) {
        this.restTemplate = restTemplate;
        this.feignRequests = feignRequests;
        this.service = service;
    }


    @GetMapping("/template/getAllUsers")
    public ResponseEntity<?> usingRestTemplate() {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers());
        ResponseEntity<?> responseEntity = restTemplate.exchange(
                "/user/getAll",
                HttpMethod.GET,
                requestEntity,
                List.class
        );

        return ResponseEntity.ok().body(responseEntity.getBody());
    }


    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        return service.getUserResponseEntity(username);

    }


    private void defaultGreeting() {
        System.out.println("Ahmed Ali is The Best");
    }

    @GetMapping("/feign/getAllUsers")

    public ResponseEntity<List<User>> usingFeign() {
        return ResponseEntity.ok().body(feignRequests.getAllUsers().getBody());
    }



    @GetMapping("/feign/updateUser")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(feignRequests.updateUser(userDTO).getBody());
    }


//    public static void createUser() {
//
//        UserDTO user = new UserDTO();
//        user.setEmail(RandomUtils.nextInt() + "@gmail.com");
//        user.setUsername(UUID.randomUUID().toString());
//        user.setFirstname(UUID.randomUUID().toString());
//        user.setPassword("password");
//        user.setGender(Gender.MALE);
//        HttpEntity<UserDTO> request = new HttpEntity<>(user, headers());
//
//        ResponseEntity<User> responseEntity = restTemplate.exchange(
//                BASE_URL + "/signup",
//                HttpMethod.POST,
//                request,
//                User.class
//        );
//
//
//        User responseUser = responseEntity.getBody();
//        log.info(responseUser.getUsername());
//
//
//    }

//    public static void updateUser() {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setBearerAuth(TOKEN_EXAMPLE);
//        HttpEntity<UserDTO> requestEntity;
//        UserDTO user = new UserDTO();
//        user.setUsername("ahmedgodaa");
//        user.setId(UUID.fromString(ID_EXAMPLE));
//        user.setLastname(UUID.randomUUID().toString());
//        requestEntity = new HttpEntity<>(user, headers());
//        ResponseEntity<User> responseEntity = restTemplate.exchange(
//                BASE_URL + "/user/update",
//                HttpMethod.PUT,
//                requestEntity,
//                User.class
//        );
//        User responseUser = responseEntity.getBody();
//        log.info(responseUser.getLastname());
//
//
//    }


//    public static void getSingleUser() {
//        HttpEntity<String> requestEntity = new HttpEntity<>(headers());
//        ResponseEntity<?> responseEntity = restTemplate.exchange(
//                BASE_URL + "/user/getAll",
//                HttpMethod.GET,
//                requestEntity,
//                String.class
//        );
//
//        log.info(responseEntity.toString());
//
//
//    }


    public static HttpHeaders headers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(TOKEN_EXAMPLE);
        return httpHeaders;


    }


}
