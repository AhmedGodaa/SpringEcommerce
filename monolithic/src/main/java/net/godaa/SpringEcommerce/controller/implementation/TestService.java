package net.godaa.SpringEcommerce.controller.implementation;

import net.godaa.SpringEcommerce.domain.User;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import static net.godaa.SpringEcommerce.security.jwt.SecurityConstants.TOKEN_EXAMPLE;

@Service
public class TestService {

    RestTemplate restTemplate;

    public TestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<User> getUserResponseEntity(String username) {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers());
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                "/user/{username}",
                HttpMethod.GET,
                requestEntity,
                User.class,
                username
        );
        System.out.println("ahmed aly maybe the best");
        return ResponseEntity.ok().body(responseEntity.getBody());
    }




    public static HttpHeaders headers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(TOKEN_EXAMPLE);
        return httpHeaders;


    }
}
