package com.meeting.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingCreateRequest {
    @NotBlank(message = "会议标题不能为空")
    private String title;

    @NotBlank(message = "会议类型不能为空")
    private String type;

    @NotNull(message = "会议时间不能为空")
    private LocalDateTime time;

    private Integer duration;
    private String location;
    private List<String> participants;
    private String description;
}
