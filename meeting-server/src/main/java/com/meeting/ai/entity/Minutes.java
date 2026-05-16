package com.meeting.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("minutes_detail")
public class Minutes {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long meetingId;
    private String title;
    private String summary;
    private String decisions;
    private String actionItems;
    private String keyPoints;
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
