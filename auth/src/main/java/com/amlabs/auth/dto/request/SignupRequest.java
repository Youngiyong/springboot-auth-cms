package com.amlabs.auth.dto.request;

import lombok.Getter;
import java.util.Set;

@Getter
public class SignupRequest {

    private String username;
    private String email;
    private Set<String> roles;
    private String password;

}
