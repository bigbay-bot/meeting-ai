package com.meeting.ai.controller;

import com.meeting.ai.dto.response.UserInfoResponse;
import com.meeting.ai.service.UserService;
import com.meeting.ai.utils.JwtUtil;
import com.meeting.ai.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/info")
    public Result<UserInfoResponse> info(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Result.fail(401, "未登录");
        }
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        return Result.ok(userService.getUserInfo(userId));
    }
}
