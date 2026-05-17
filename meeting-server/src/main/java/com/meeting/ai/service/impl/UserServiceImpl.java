package com.meeting.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meeting.ai.dto.request.LoginRequest;
import com.meeting.ai.dto.request.RegisterRequest;
import com.meeting.ai.dto.response.LoginResponse;
import com.meeting.ai.dto.response.UserInfoResponse;
import com.meeting.ai.entity.User;
import com.meeting.ai.exception.BusinessException;
import com.meeting.ai.mapper.UserMapper;
import com.meeting.ai.service.UserService;
import com.meeting.ai.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", request.getUsername())
        );
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("账户已被禁用");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(String.valueOf(user.getId()));
        response.setUsername(user.getUsername());
        response.setAvatar(user.getAvatar());
        return response;
    }

    @Override
    public void register(RegisterRequest request) {
        Long count = userMapper.selectCount(
                new QueryWrapper<User>().eq("username", request.getUsername())
        );
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        Long emailCount = userMapper.selectCount(
                new QueryWrapper<User>().eq("email", request.getEmail())
        );
        if (emailCount > 0) {
            throw new BusinessException("邮箱已被注册");
        }

        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("user");
        user.setStatus(1);
        userMapper.insert(user);
    }

    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        UserInfoResponse response = new UserInfoResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
