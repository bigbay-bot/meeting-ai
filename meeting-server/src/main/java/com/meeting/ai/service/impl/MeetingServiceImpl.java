package com.meeting.ai.service.impl;

import com.meeting.ai.dto.request.MeetingCreateRequest;
import com.meeting.ai.dto.request.MeetingListRequest;
import com.meeting.ai.dto.response.MeetingDetailResponse;
import com.meeting.ai.dto.response.PageResult;
import com.meeting.ai.service.MeetingService;
import org.springframework.stereotype.Service;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Override
    public PageResult<MeetingDetailResponse> list(MeetingListRequest request) {
        return new PageResult<>();
    }

    @Override
    public MeetingDetailResponse getById(Long id) {
        return new MeetingDetailResponse();
    }

    @Override
    public Long create(MeetingCreateRequest request) {
        return 1L;
    }

    @Override
    public void update(Long id, MeetingCreateRequest request) {
    }

    @Override
    public void delete(Long id) {
    }
}
