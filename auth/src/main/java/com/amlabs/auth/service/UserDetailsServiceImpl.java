package com.amlabs.auth.service;

import com.amlabs.auth.dto.BaseResponse;
import com.amlabs.auth.dto.request.SignupRequest;
import com.amlabs.auth.entity.UserEntity;
import com.amlabs.auth.entity.UserRoleEntity;
import com.amlabs.auth.entity.UserTokenEntity;
import com.amlabs.auth.exception.CustomException;
import com.amlabs.auth.model.ERole;
import com.amlabs.auth.repository.UserRepository;
import com.amlabs.auth.constants.*;
import com.amlabs.auth.repository.UserRoleRepository;
import com.amlabs.auth.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " can not be found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (ERole.ROLE_ADMIN.name().equals(user)) {
            authorities.add(new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name()));
        } else if (ERole.ROLE_MANAGER.name().equals(user)){
            authorities.add(new SimpleGrantedAuthority(ERole.ROLE_MANAGER.name()));
        } else {
            authorities.add(new SimpleGrantedAuthority(ERole.ROLE_MANAGER.name()));
        }

        return UserDetailsImpl.build(user);
    }

    @Transactional
    public BaseResponse save(SignupRequest payload) throws UsernameNotFoundException {
        if (userRepository.existsByUsername(payload.getUsername()))
            throw new CustomException(CustomExceptionCode.EMAIL_IS_EXIST);

        if (userRepository.existsByUsername(payload.getUsername()))
            throw new CustomException(CustomExceptionCode.USERNAME_IS_EXIST);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserEntity user = UserEntity.builder()
                .email(payload.getEmail())
                .username(payload.getEmail())
                .password(encoder.encode(payload.getPassword()))
                .build();

        userRepository.save(user);

        Set<String> strRoles = payload.getRole();
        Set<UserRoleEntity> roles = new HashSet<>();

        if (strRoles == null){
            UserRoleEntity userRole = userRoleRepository.findByName(ERole.ROLE_USER).get();

            if(userRole==null)
                throw new CustomException(CustomExceptionCode.REQUEST_ROLE_NOT_FOUND);

            roles.add(userRole);

        } else {
            for (String role: strRoles){
                switch (role) {
                    case "admin":
                        UserRoleEntity adminRole = userRoleRepository.findByName(ERole.ROLE_ADMIN).get();

                        if(adminRole==null){
                            throw new CustomException(CustomExceptionCode.REQUEST_ROLE_NOT_FOUND);
                        }
                        roles.add(adminRole);

                        break;
                    case "manager":
                        UserRoleEntity managerRole = userRoleRepository.findByName(ERole.ROLE_MANAGER).get();

                        if(managerRole==null){
                            throw new CustomException(CustomExceptionCode.REQUEST_ROLE_NOT_FOUND);
                        }
                        roles.add(managerRole);

                        break;
                    default:
                        UserRoleEntity userRole = userRoleRepository.findByName(ERole.ROLE_USER).get();

                        if(userRole==null){
                            throw new CustomException(CustomExceptionCode.REQUEST_ROLE_NOT_FOUND);
                        }

                        roles.add(userRole);

                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new BaseResponse(true, "reg ok");

    }
}
