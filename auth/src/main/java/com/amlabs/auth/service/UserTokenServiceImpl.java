package com.amlabs.auth.service;

import com.amlabs.auth.constants.CustomExceptionCode;
import com.amlabs.auth.entity.UserTokenEntity;
import com.amlabs.auth.exception.CustomException;
import com.amlabs.auth.repository.UserRepository;
import com.amlabs.auth.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserTokenServiceImpl {

    @Value("${spring.jwt.accessTokenExpirationMs}")
    private int jwtAccessTokenExpirationMs;

    @Value("${spring.jwt.refreshExpirationMs}")
    private int jwtRefreshTokenExpirationMs;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<UserTokenEntity> findByToken(String token) {
        return userTokenRepository.findByToken(token);
    }

    public UserTokenEntity createRefreshToken(Long userId, String accessToken) {
        UserTokenEntity refreshToken = new UserTokenEntity();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshTokenExpirationMs));
        refreshToken.setToken(accessToken);

        refreshToken = userTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public UserTokenEntity verifyExpiration(UserTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            userTokenRepository.delete(token);
            throw new CustomException(CustomExceptionCode.EXPIRE_REFRESH_TOKEN);
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return userTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

}
