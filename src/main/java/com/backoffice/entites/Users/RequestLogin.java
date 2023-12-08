package com.backoffice.entites.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestLogin {

    private String username;
    private String password;
    private String token;
    private String role;

}
