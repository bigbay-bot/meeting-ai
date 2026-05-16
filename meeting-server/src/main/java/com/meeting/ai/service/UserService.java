package com.meeting.ai.service;

import com.meeting.ai.dto.request.LoginRequest;
import com.meeting.ai.dto.response.LoginResponse;

public interface UserService {
    LoginResponse login(LoginRequest request);
}
