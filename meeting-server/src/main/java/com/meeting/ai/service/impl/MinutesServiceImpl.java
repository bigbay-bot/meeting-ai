package com.meeting.ai.service.impl;

import com.meeting.ai.dto.response.MinutesResponse;
import com.meeting.ai.service.MinutesService;
import org.springframework.stereotype.Service;

@Service
public class MinutesServiceImpl implements MinutesService {

    @Override
    public MinutesResponse generate(Long meetingId) {
        return new MinutesResponse();
    }

    @Override
    public MinutesResponse getById(Long id) {
        return new MinutesResponse();
    }

    @Override
    public void update(Long id, MinutesResponse request) {
    }
}
