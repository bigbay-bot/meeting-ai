package com.meeting.ai.service;

import com.meeting.ai.dto.request.AiChatRequest;
import com.meeting.ai.dto.response.AiChatResponse;

public interface AiService {
    AiChatResponse chat(AiChatRequest request);
}
