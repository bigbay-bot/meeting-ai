package com.meeting.ai.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AICrossAnalysisResponse {

    private String sessionId;

    private String content;

    private List<Insight> insights;

    @Data
    public static class Insight {
        private Long meetingId;
        private String meetingTitle;
        private List<String> keyPoints;
    }
}
