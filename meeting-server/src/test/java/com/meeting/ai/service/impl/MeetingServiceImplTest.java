package com.meeting.ai.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.ai.dto.request.MeetingListRequest;
import com.meeting.ai.dto.response.MeetingDetailResponse;
import com.meeting.ai.dto.response.PageResult;
import com.meeting.ai.entity.Meeting;
import com.meeting.ai.entity.User;
import com.meeting.ai.mapper.MeetingMapper;
import com.meeting.ai.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingServiceImplTest {

    @Mock
    private MeetingMapper meetingMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private MeetingServiceImpl meetingService;

    @Test
    void list_shouldReturnPagedResultsWithOrganizerName() {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("校园网安全专项工作会");
        meeting.setOrganizerId(100L);
        meeting.setType("project");
        meeting.setStatus("completed");
        meeting.setStartTime(LocalDateTime.of(2026, 5, 15, 14, 0));
        meeting.setEndTime(LocalDateTime.of(2026, 5, 15, 16, 0));
        meeting.setDuration(120);
        meeting.setLocation("线上会议");

        Page<Meeting> page = new Page<>(1, 10, 1);
        page.setRecords(List.of(meeting));

        when(meetingMapper.selectPage(any(Page.class), any())).thenReturn(page);

        User user = new User();
        user.setId(100L);
        user.setRealName("张晓明");
        when(userMapper.selectBatchIds(List.of(100L))).thenReturn(List.of(user));

        MeetingListRequest request = new MeetingListRequest();
        request.setPage(1);
        request.setSize(10);
        request.setType("project");

        PageResult<MeetingDetailResponse> result = meetingService.list(request);

        assertNotNull(result);
        assertEquals(1L, result.getTotal());
        assertEquals(1, result.getRecords().size());

        MeetingDetailResponse dto = result.getRecords().get(0);
        assertEquals(1L, dto.getId());
        assertEquals("校园网安全专项工作会", dto.getTitle());
        assertEquals("张晓明", dto.getOrganizer());
        assertEquals("project", dto.getType());
        assertEquals("2026-05-15 14:00 - 2026-05-15 16:00", dto.getTime());
    }

    @Test
    void list_shouldReturnEmptyWhenNoMeetings() {
        Page<Meeting> emptyPage = new Page<>(1, 10, 0);
        emptyPage.setRecords(Collections.emptyList());

        when(meetingMapper.selectPage(any(Page.class), any())).thenReturn(emptyPage);

        MeetingListRequest request = new MeetingListRequest();
        request.setPage(1);
        request.setSize(10);

        PageResult<MeetingDetailResponse> result = meetingService.list(request);

        assertNotNull(result);
        assertEquals(0L, result.getTotal());
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    void list_shouldFilterByType() {
        Meeting meeting = new Meeting();
        meeting.setId(2L);
        meeting.setTitle("技术架构讨论会");
        meeting.setOrganizerId(200L);
        meeting.setType("review");
        meeting.setStatus("completed");
        meeting.setStartTime(LocalDateTime.of(2026, 5, 14, 10, 0));
        meeting.setEndTime(LocalDateTime.of(2026, 5, 14, 11, 30));
        meeting.setDuration(90);

        Page<Meeting> page = new Page<>(1, 10, 1);
        page.setRecords(List.of(meeting));

        when(meetingMapper.selectPage(any(Page.class), argThat(qw -> qw.getSqlSegment().contains("type = 'review'")))).thenReturn(page);

        User user = new User();
        user.setId(200L);
        user.setUsername("lihua");
        when(userMapper.selectBatchIds(List.of(200L))).thenReturn(List.of(user));

        MeetingListRequest request = new MeetingListRequest();
        request.setType("review");

        PageResult<MeetingDetailResponse> result = meetingService.list(request);

        assertEquals(1, result.getRecords().size());
        assertEquals("review", result.getRecords().get(0).getType());
    }
}
