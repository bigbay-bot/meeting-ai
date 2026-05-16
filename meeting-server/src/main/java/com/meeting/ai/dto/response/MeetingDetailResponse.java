package com.meeting.ai.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingDetailResponse {
    private Long id;
    private String title;
    private String organizer;
    private LocalDateTime time;
    private String duration;
    private String location;
    private List<String> participants;
    private String status;
    private String type;
}
