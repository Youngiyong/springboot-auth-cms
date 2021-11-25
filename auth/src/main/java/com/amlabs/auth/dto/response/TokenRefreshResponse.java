package com.amlabs.auth.dto.response;

import lombok.Data;

@Data
public class TokenRefreshResponse {
  private String accessToken;
  private String refreshToken;
  private String tokenType = "Bearer";
}
