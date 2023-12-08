package com.backoffice.services.users;

import com.backoffice.entites.Users.User;
import com.backoffice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        User user = userRepository.findByEmail(username).orElseThrow(()-> new IllegalArgumentException("Invalid username"));
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("User non valide");
        }else{
            authorities.add(new SimpleGrantedAuthority(user.getERole().getNom()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
