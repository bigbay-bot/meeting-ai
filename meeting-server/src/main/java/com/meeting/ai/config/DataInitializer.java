package com.meeting.ai.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meeting.ai.entity.User;
import com.meeting.ai.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initUser("admin", "admin@meeting-ai.com", "系统管理员", "admin", "技术部", "系统管理员");
        initUser("zhangxiaoming", "zhangxiaoming@meeting-ai.com", "张晓明", "user", "产品部", "产品设计师");
    }

    private void initUser(String username, String email, String realName, String role, String department, String position) {
        Long count = userMapper.selectCount(new QueryWrapper<User>().eq("username", username));
        if (count > 0) {
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRealName(realName);
        user.setRole(role);
        user.setDepartment(department);
        user.setPosition(position);
        user.setStatus(1);
        user.setPassword(passwordEncoder.encode("admin123"));
        userMapper.insert(user);
    }
}
