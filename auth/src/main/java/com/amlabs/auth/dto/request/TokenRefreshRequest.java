package com.amlabs.auth.dto.request;

import lombok.Getter;

import java.util.Set;

@Getter
public class TokenRefreshRequest {

    private String refreshToken;

}
