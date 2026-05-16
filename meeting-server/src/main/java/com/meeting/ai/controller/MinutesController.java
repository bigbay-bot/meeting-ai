package com.meeting.ai.controller;

import com.meeting.ai.dto.response.MinutesResponse;
import com.meeting.ai.service.MinutesService;
import com.meeting.ai.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MinutesController {

    private final MinutesService minutesService;

    @PostMapping("/meetings/{meetingId}/minutes/generate")
    public Result<MinutesResponse> generate(@PathVariable Long meetingId) {
        return Result.ok(minutesService.generate(meetingId));
    }

    @GetMapping("/minutes/{id}")
    public Result<MinutesResponse> getById(@PathVariable Long id) {
        return Result.ok(minutesService.getById(id));
    }

    @PutMapping("/minutes/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody MinutesResponse request) {
        minutesService.update(id, request);
        return Result.ok();
    }
}
