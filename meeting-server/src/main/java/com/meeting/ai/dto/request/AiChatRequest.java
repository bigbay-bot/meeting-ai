package com.meeting.ai.dto.request;

import lombok.Data;

@Data
public class AiChatRequest {
    private String sessionId;
    private String message;
    private Long meetingId;
}
