package net.godaa.SpringEcommerce.security.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
@Slf4j
public class UserServiceDetailsImpl implements UserDetailsService {
    @Autowired
    UserService userService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no user with username: " + username));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(role -> authorities.add(new SimpleGrantedAuthority(String.valueOf(role.getName()))));
        return UserDetailsImpl.build(user);

    }


}
