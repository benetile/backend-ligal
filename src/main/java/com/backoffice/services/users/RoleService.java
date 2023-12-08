package com.backoffice.services.users;

import com.backoffice.entites.Users.ERole;
import com.backoffice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public static final List<String> roles = Arrays.asList("ROLE_ADMIN","ROLE_COMMERCIAL","ROLE_CLIENT");
    public void initialisation(){
        roles.forEach(s -> {
            ERole eRole = new ERole();
            eRole.setNom(s);
            if (roleRepository.findByNom(s).isEmpty()){
                roleRepository.save(eRole);
            }
        });
    }
}
