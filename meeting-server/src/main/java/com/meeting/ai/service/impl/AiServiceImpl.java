package com.meeting.ai.service.impl;

import com.meeting.ai.dto.request.AiChatRequest;
import com.meeting.ai.dto.response.AiChatResponse;
import com.meeting.ai.service.AiService;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService {

    @Override
    public AiChatResponse chat(AiChatRequest request) {
        AiChatResponse response = new AiChatResponse();
        response.setSessionId(request.getSessionId());
        response.setContent("AI 回复内容占位");
        response.setDone(true);
        return response;
    }
}
