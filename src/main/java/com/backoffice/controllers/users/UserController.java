package com.backoffice.controllers.users;

import com.backoffice.entites.Account;
import com.backoffice.entites.Users.User;
import com.backoffice.repositories.UserRepository;
import com.backoffice.services.account.AccountService;
import com.backoffice.services.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/current")
    public User currentUser(Principal principal){
        logger.info(principal.getName()+ " connecté");
        return userRepository.findByEmail(principal.getName()).orElseThrow(()-> new IllegalArgumentException("invalid User"));
    }

    @GetMapping("/check/{username}")
    public Boolean verifierUsername(@PathVariable("username") String username){
        return userRepository.findByUsername(username).isPresent();
    }

    @PostMapping("/creation")
    public User creationUser(@RequestBody User user){
        return userService.creationUserEtAccount(user);
    }

}
