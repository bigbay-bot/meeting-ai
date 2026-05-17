package com.meeting.ai.dto.request;

import lombok.Data;

@Data
public class MeetingListRequest {
    private Integer page = 1;
    private Integer size = 10;
    private String keyword;
    private String status;
    private String type;
    private String startTime;
    private String endTime;
}
