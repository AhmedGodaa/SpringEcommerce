package net.godaa.SpringEcommerce.controller;

import feign.Headers;
import jakarta.validation.Valid;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;
import net.godaa.SpringEcommerce.service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "figenRequests", url = SecurityConstants.BASE_URL + "/user")

public interface FeignRequests {

    @GetMapping(value = "/getAll")
    ResponseEntity<List<User>> getAllUsers();

    @PutMapping(value = "/update")
    ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO);
}

