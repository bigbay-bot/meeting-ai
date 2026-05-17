package com.meeting.ai.service;

import com.meeting.ai.dto.request.AICrossAnalysisRequest;
import com.meeting.ai.dto.request.AiChatRequest;
import com.meeting.ai.dto.response.AICrossAnalysisResponse;
import com.meeting.ai.dto.response.AiChatResponse;

public interface AiService {
    AiChatResponse chat(AiChatRequest request);

    AICrossAnalysisResponse crossAnalysis(AICrossAnalysisRequest request);
}
