package com.amlabs.auth.controller.v1;

import com.amlabs.auth.dto.BaseResponse;
import com.amlabs.auth.dto.request.SignupRequest;
import com.amlabs.auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserDetailsServiceImpl userService;

    @PostMapping("/signup")
    public BaseResponse registerUser(@RequestBody SignupRequest payload){
        return userService.save(payload);
    }
}
