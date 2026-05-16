package com.meeting.ai.controller;

import com.meeting.ai.dto.request.AiChatRequest;
import com.meeting.ai.dto.response.AiChatResponse;
import com.meeting.ai.service.AiService;
import com.meeting.ai.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/chat")
    public Result<AiChatResponse> chat(@RequestBody AiChatRequest request) {
        return Result.ok(aiService.chat(request));
    }

    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("ok");
    }
}
