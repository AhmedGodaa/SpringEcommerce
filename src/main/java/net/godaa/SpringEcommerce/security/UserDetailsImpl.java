package net.godaa.SpringEcommerce.security;

import lombok.extern.slf4j.Slf4j;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
@Slf4j
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {throw new UsernameNotFoundException(username);}
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(user.getRole())));
//        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(String.valueOf(role.getName()))));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


}
