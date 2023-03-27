package com.example.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoResponse {
    private String token;
    private String type = "Bearer";

    public UserInfoResponse(String accessToken) {
        this.token = accessToken;
    }


}