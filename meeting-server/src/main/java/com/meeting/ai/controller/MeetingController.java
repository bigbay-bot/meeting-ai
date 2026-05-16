package com.meeting.ai.controller;

import com.meeting.ai.dto.request.MeetingCreateRequest;
import com.meeting.ai.dto.request.MeetingListRequest;
import com.meeting.ai.dto.response.MeetingDetailResponse;
import com.meeting.ai.dto.response.PageResult;
import com.meeting.ai.service.MeetingService;
import com.meeting.ai.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping
    public Result<PageResult<MeetingDetailResponse>> list(MeetingListRequest request) {
        return Result.ok(meetingService.list(request));
    }

    @GetMapping("/{id}")
    public Result<MeetingDetailResponse> detail(@PathVariable Long id) {
        return Result.ok(meetingService.getById(id));
    }

    @PostMapping
    public Result<Long> create(@RequestBody @Validated MeetingCreateRequest request) {
        return Result.ok(meetingService.create(request));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Validated MeetingCreateRequest request) {
        meetingService.update(id, request);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        meetingService.delete(id);
        return Result.ok();
    }
}
