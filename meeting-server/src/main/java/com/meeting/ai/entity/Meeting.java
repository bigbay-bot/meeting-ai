package com.meeting.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("meeting")
public class Meeting {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private Long organizerId;

    private String type;

    private String status;

    private Integer progressPercent;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String duration;

    private String location;

    private String description;

    private String tags;

    private Integer isStarred;

    private String aiProcessingStatus;

    private Integer estimatedRemainingTime;

    private Long createdBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
