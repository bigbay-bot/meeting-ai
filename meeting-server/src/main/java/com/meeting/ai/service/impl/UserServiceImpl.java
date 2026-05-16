package com.meeting.ai.service.impl;

import com.meeting.ai.dto.request.LoginRequest;
import com.meeting.ai.dto.response.LoginResponse;
import com.meeting.ai.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public LoginResponse login(LoginRequest request) {
        // TODO: implement login logic
        LoginResponse response = new LoginResponse();
        response.setToken("mock-token");
        response.setUserId("1");
        response.setUsername(request.getUsername());
        return response;
    }
}
