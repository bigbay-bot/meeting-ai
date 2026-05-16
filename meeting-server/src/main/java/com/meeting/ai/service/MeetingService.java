package com.meeting.ai.service;

import com.meeting.ai.dto.request.MeetingCreateRequest;
import com.meeting.ai.dto.request.MeetingListRequest;
import com.meeting.ai.dto.response.MeetingDetailResponse;
import com.meeting.ai.dto.response.PageResult;

public interface MeetingService {
    PageResult<MeetingDetailResponse> list(MeetingListRequest request);
    MeetingDetailResponse getById(Long id);
    Long create(MeetingCreateRequest request);
    void update(Long id, MeetingCreateRequest request);
    void delete(Long id);
}
