package com.backoffice.controllers.users;

import com.backoffice.config.JwtUtil;
import com.backoffice.entites.Users.RequestLogin;
import com.backoffice.entites.Users.User;
import com.backoffice.repositories.UserRepository;
import com.backoffice.services.users.MyUserDetailsService;
import com.backoffice.services.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public String login(@RequestBody RequestLogin requestLogin) throws Exception {
        String token;
        try {
            logger.info("Tentative de connexion "+requestLogin.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestLogin.getUsername(), requestLogin.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Login ");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(requestLogin.getUsername());
        User user = userRepository.findByEmail(requestLogin.getUsername()).orElseThrow(()-> new IllegalArgumentException("Invalid user"));
        //System.out.println("Token envoyer **************************** "+jwtUtil.generateToken(request.getUsername()));
        logger.info("Fin de l'authentification ");
        return jwtUtil.generateToken(user);
    }

    @GetMapping("/logout")
    public String logOut(){
        logger.info("Deconnexion  ");
        return "deconnexion";
    }
}
