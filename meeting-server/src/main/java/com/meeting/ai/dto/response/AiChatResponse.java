package com.meeting.ai.dto.response;

import lombok.Data;

@Data
public class AiChatResponse {
    private String sessionId;
    private String content;
    private Boolean done;
}
