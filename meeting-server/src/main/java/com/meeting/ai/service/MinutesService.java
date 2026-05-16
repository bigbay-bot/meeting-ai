package com.meeting.ai.service;

import com.meeting.ai.dto.response.MinutesResponse;

public interface MinutesService {
    MinutesResponse generate(Long meetingId);
    MinutesResponse getById(Long id);
    void update(Long id, MinutesResponse request);
}
