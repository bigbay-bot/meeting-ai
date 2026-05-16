package com.meeting.ai.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MinutesResponse {
    private Long id;
    private Long meetingId;
    private String title;
    private String summary;
    private List<String> participants;
    private List<String> decisions;
    private List<ActionItemVO> actionItems;
    private List<String> keyPoints;
    private String status;
}
