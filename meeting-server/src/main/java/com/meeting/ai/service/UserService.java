package com.meeting.ai.service;

import com.meeting.ai.dto.request.LoginRequest;
import com.meeting.ai.dto.request.RegisterRequest;
import com.meeting.ai.dto.response.LoginResponse;
import com.meeting.ai.dto.response.UserInfoResponse;

public interface UserService {
    LoginResponse login(LoginRequest request);

    void register(RegisterRequest request);

    UserInfoResponse getUserInfo(Long userId);
}
