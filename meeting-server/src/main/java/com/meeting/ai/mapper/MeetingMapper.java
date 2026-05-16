package com.meeting.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meeting.ai.entity.Meeting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MeetingMapper extends BaseMapper<Meeting> {
}
