package com.amlabs.auth.repository;

import com.amlabs.auth.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(Enum name);
}
