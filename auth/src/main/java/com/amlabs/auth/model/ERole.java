package com.amlabs.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ERole {
    ROLE_USER,
    ROLE_MANAGER,
    ROLE_ADMIN
}
