package com.meeting.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("meeting_minutes")
public class Minutes {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long meetingId;

    private Integer version;

    private String contentType;

    private String title;

    private String summary;

    private String keyDecisions;

    private String actionItems;

    private String controversies;

    private String consensus;

    private String fullContent;

    private Integer generatedByAi;

    private String aiModelUsed;

    private String aiPromptVersion;

    private Integer generationTime;

    private Long editorId;

    private Integer isFinalized;

    private LocalDateTime finalizedAt;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
