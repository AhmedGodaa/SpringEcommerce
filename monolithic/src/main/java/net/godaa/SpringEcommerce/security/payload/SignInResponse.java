package net.godaa.SpringEcommerce.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SignInResponse {

    private String username;

    private boolean enabled;

    private String token;

    private List<String> authorities;


}
