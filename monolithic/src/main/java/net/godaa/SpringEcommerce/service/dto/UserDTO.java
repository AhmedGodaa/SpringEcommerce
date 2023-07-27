package net.godaa.SpringEcommerce.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.godaa.SpringEcommerce.domain.Authority;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.domain.enums.Gender;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
public class UserDTO {
    private UUID id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String image;

    private boolean activated = false;

    private String password;
    private Set<String> authorities;

    private Gender gender;

    private String phone;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String country;


    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.image = user.getImage();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

}
