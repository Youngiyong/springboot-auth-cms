package com.amlabs.auth.controller.v1;

import com.amlabs.auth.dto.BaseResponse;
import com.amlabs.auth.dto.request.LoginRequest;
import com.amlabs.auth.dto.request.SignupRequest;
import com.amlabs.auth.entity.UserTokenEntity;
import com.amlabs.auth.jwt.JwtUtils;
import com.amlabs.auth.service.UserDetailsImpl;
import com.amlabs.auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userService;

    @PostMapping("/signup")
    public BaseResponse registerUser(@RequestBody SignupRequest payload){
        return userService.save(payload);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new ResponseEntity(200, HttpStatus.valueOf("OK"));
//        UserTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//
//        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
//                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

}
