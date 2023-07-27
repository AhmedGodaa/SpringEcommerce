package net.godaa.SpringEcommerce.controller;

import jakarta.validation.Valid;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.exceptions.EmailAlreadyUsedException;
import net.godaa.SpringEcommerce.exceptions.UsernameAlreadyUsedException;
import net.godaa.SpringEcommerce.repository.UserRepo;
import net.godaa.SpringEcommerce.security.jwt.JwtUtils;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;
import net.godaa.SpringEcommerce.security.payload.JwtResponse;
import net.godaa.SpringEcommerce.security.refreshToken.RefreshToken;
import net.godaa.SpringEcommerce.security.refreshToken.RefreshTokenService;
import net.godaa.SpringEcommerce.security.service.UserDetailsImpl;
import net.godaa.SpringEcommerce.service.UserService;
import net.godaa.SpringEcommerce.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Controller
@RequestMapping
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(UserControllerImpl.class);


    UserRepo userRepo;

    UserService userService;


    AuthenticationManager authenticationManager;


    JwtUtils jwtUtils;



    RefreshTokenService refreshTokenService;

    public AuthController(UserRepo userRepo, UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("/authorities")
    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }




    @PostMapping("/signup")
//    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public ResponseEntity<User> signUp(@Valid @RequestBody UserDTO userDTO, @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new EmailAlreadyUsedException();
        } else if (userRepo.findByUsername(userDTO.getUsername().toLowerCase()).isPresent()) {
            throw new UsernameAlreadyUsedException();
        } else if (userRepo.findByEmail(userDTO.getEmail().toLowerCase()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
                userDTO.setId(UUID.randomUUID());
            User user = userService.createUser(userDTO);
            try {
                if (multipartFile != null) {
                    userService.addImageToUser(userDTO.getUsername(), multipartFile);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // TODO: Add Mail Service
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }


    @PostMapping("/signin")
//    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public ResponseEntity<?> signIn(@Valid @RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));


    }

}
