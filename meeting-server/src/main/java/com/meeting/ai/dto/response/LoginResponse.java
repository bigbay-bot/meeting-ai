package com.meeting.ai.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String userId;
    private String username;
    private String avatar;
}
