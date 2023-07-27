//package net.godaa.SpringEcommerce.security.jwt;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.godaa.SpringEcommerce.security.payload.SignInResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//import static net.godaa.SpringEcommerce.security.jwt.SecurityConstants.*;
//import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
//
//public class AuthFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//
//
//    private final UserDetailsService userDetailsService;
//
//    public AuthFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//    }
//
//    // Check if username and password are valid
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
//        return authenticationManager.authenticate(authentication);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
//        User user = (User) authResult.getPrincipal();
////        Create access_token
//        String access_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withIssuedAt(new Date())
//                .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(EXPIRATION_TIME)))
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(Algorithm.HMAC256(KEY.getBytes()));
////        Add token to header
//        response.setHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + access_token);
////        Set response type to "application/json"
//        response.setContentType(APPLICATION_JSON_VALUE);
//
//
//        SignInResponse signInResponse = new SignInResponse(
//                user.getUsername(),
//                user.isCredentialsNonExpired(),
//                access_token,
//                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
//        new ObjectMapper().writeValue(response.getOutputStream(), signInResponse);
//    }
//
//}
