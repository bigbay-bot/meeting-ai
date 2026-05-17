package com.meeting.ai.dto.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String realName;
    private String avatar;
    private String role;
    private String department;
    private String position;
}
