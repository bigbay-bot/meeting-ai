package com.meeting.ai.enums;

import lombok.Getter;

@Getter
public enum MeetingStatus {
    PENDING("pending", "待开始"),
    ONGOING("ongoing", "进行中"),
    TRANSCRIBED("transcribed", "待生成纪要"),
    COMPLETED("completed", "已完成");

    private final String code;
    private final String desc;

    MeetingStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
