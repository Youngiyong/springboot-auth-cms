package com.amlabs.auth.repository;

import com.amlabs.auth.entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {
    Optional<UserTokenEntity> findByToken(String token);

//    @Modifying
//    int deleteByUser(User user);
}
