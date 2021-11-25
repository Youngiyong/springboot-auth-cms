package com.amlabs.auth.service;


import com.amlabs.auth.dto.request.SignupRequest;
import com.amlabs.auth.entity.UserEntity;
import com.amlabs.auth.model.ERole;
import com.amlabs.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user =  userRepository.findByEmail(email);
        System.out.println(user);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (ERole.ROLE_ADMIN.name().equals(user)) {
            authorities.add(new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name()));
        } else if (ERole.ROLE_MANAGER.name().equals(user)){
            authorities.add(new SimpleGrantedAuthority(ERole.ROLE_MANAGER.name()));
        } else {
            authorities.add(new SimpleGrantedAuthority(ERole.ROLE_MANAGER.name()));
        }

        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Transactional
    public Long save(SignupRequest payload) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserEntity user = new UserEntity();
        user.setEmail(payload.getEmail());
        user.setPassword(encoder.encode(payload.getPassword()));


//        user.setRoles(payload.getRoles());

        return user.getId();

    }
//
//    public List<UserEntity> findAll(){
//        return usersRepository.findAll();
//    }
//
//    public UserEntity findById(Long id){
//        return usersRepository.findById(id).get();
////        Users user =  usersRepository.findById(id).get();
////        return Users.builder().email(user.getEmail())
////                                        .auth(user.getAuth())
////                                        .userProfile(user.getUserProfile())
////                                        .createdAt(user.getCreatedAt());
//    }
//
//    public UserEntity update(Long id, Dto.RequestUpdateUser payload){
//        UserEntity user = usersRepository.findById(id).get();
//        user.setName(payload.getName());
//        user.setUpdatedAt(LocalDateTime.now());
//        usersRepository.save(user);
//
//        return user;
//    }
//
//    public void delete(Long id){
//        UserEntity user = usersRepository.findById(id).get();
//        usersRepository.delete(user);
//    }
}
