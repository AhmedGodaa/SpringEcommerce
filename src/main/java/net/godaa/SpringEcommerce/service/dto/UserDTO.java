package net.godaa.SpringEcommerce.service.dto;

import lombok.*;
import net.godaa.SpringEcommerce.domain.Authority;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.domain.enums.Gender;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;

import javax.validation.constraints.*;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = SecurityConstants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String username;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @NotBlank
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String image;

    private boolean activated = false;

    @NotNull
    @Size(min = 60, max = 60)
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
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.image = user.getImage();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

}
