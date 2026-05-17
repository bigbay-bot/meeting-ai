package com.meeting.ai.controller;

import com.meeting.ai.dto.request.LoginRequest;
import com.meeting.ai.dto.request.RegisterRequest;
import com.meeting.ai.dto.response.LoginResponse;
import com.meeting.ai.service.UserService;
import com.meeting.ai.utils.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        return Result.ok(userService.login(request));
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterRequest request) {
        userService.register(request);
        return Result.ok();
    }
}
