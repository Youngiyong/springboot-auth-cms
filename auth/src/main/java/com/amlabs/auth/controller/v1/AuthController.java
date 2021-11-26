package com.amlabs.auth.controller.v1;

import com.amlabs.auth.dto.BaseResponse;
import com.amlabs.auth.dto.request.LoginRequest;
import com.amlabs.auth.dto.request.SignupRequest;
import com.amlabs.auth.dto.response.JwtResponse;
import com.amlabs.auth.dto.response.MessageResponse;
import com.amlabs.auth.entity.UserEntity;
import com.amlabs.auth.entity.UserTokenEntity;
import com.amlabs.auth.jwt.JwtUtils;
import com.amlabs.auth.service.UserDetailsImpl;
import com.amlabs.auth.service.UserDetailsServiceImpl;
import com.amlabs.auth.service.UserTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    @Autowired
    UserTokenServiceImpl userTokenService;

    @Value("${spring.jwt.secret}")
    private String jwtSecret;

    @Value("${spring.jwt.accessTokenExpirationMs}")
    private int jwtAccessTokenExpirationMs;

    @Value("${spring.jwt.refreshExpirationMs}")
    private int jwtRefreshTokenExpirationMs;

    @PostMapping("/signup")
    public BaseResponse registerUser(@RequestBody SignupRequest payload){
        return userService.save(payload);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtAccessToken = jwtUtils.generateAccessToken(userDetails);
        String jwtRefreshToken = jwtUtils.generateRefreshToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        userTokenService.createRefreshToken(userDetails.getId(), jwtAccessToken);

        Cookie accessCookie = new Cookie("access_token", jwtAccessToken);
        Cookie refreshCookie = new Cookie("refresh_token", jwtRefreshToken);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(jwtAccessTokenExpirationMs);

        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(jwtRefreshTokenExpirationMs);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        return ResponseEntity.ok(new JwtResponse(jwtAccessToken, jwtRefreshToken, userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

}
