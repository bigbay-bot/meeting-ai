package com.meeting.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meeting.ai.dto.request.MeetingCreateRequest;
import com.meeting.ai.dto.request.MeetingListRequest;
import com.meeting.ai.dto.response.MeetingDetailResponse;
import com.meeting.ai.dto.response.PageResult;
import com.meeting.ai.entity.Meeting;
import com.meeting.ai.entity.User;
import com.meeting.ai.mapper.MeetingMapper;
import com.meeting.ai.mapper.UserMapper;
import com.meeting.ai.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingMapper meetingMapper;
    private final UserMapper userMapper;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public PageResult<MeetingDetailResponse> list(MeetingListRequest request) {
        QueryWrapper<Meeting> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("created_at");

        if (StringUtils.hasText(request.getType())) {
            wrapper.eq("type", request.getType());
        }
        if (StringUtils.hasText(request.getStatus())) {
            wrapper.eq("status", request.getStatus());
        }
        if (StringUtils.hasText(request.getKeyword())) {
            wrapper.like("title", request.getKeyword());
        }

        Page<Meeting> pageParam = new Page<>(request.getPage(), request.getSize());
        Page<Meeting> page = meetingMapper.selectPage(pageParam, wrapper);

        List<MeetingDetailResponse> records = convertToResponse(page.getRecords());

        PageResult<MeetingDetailResponse> result = new PageResult<>();
        result.setRecords(records);
        result.setTotal(page.getTotal());
        result.setPage((int) page.getCurrent());
        result.setSize((int) page.getSize());
        return result;
    }

    @Override
    public MeetingDetailResponse getById(Long id) {
        Meeting meeting = meetingMapper.selectById(id);
        if (meeting == null) {
            return null;
        }
        List<MeetingDetailResponse> list = convertToResponse(Collections.singletonList(meeting));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Long create(MeetingCreateRequest request) {
        Meeting meeting = new Meeting();
        BeanUtils.copyProperties(request, meeting);
        meeting.setStatus("scheduled");
        meeting.setProgressPercent(0);
        meeting.setAiProcessingStatus("pending");
        if (request.getTime() != null) {
            meeting.setStartTime(request.getTime());
            if (request.getDuration() != null) {
                meeting.setEndTime(request.getTime().plusMinutes(request.getDuration()));
            }
        }
        meetingMapper.insert(meeting);
        return meeting.getId();
    }

    @Override
    public void update(Long id, MeetingCreateRequest request) {
        Meeting meeting = meetingMapper.selectById(id);
        if (meeting == null) {
            return;
        }
        meeting.setTitle(request.getTitle());
        meeting.setType(request.getType());
        meeting.setLocation(request.getLocation());
        meeting.setDescription(request.getDescription());
        meeting.setDuration(request.getDuration());
        if (request.getTime() != null) {
            meeting.setStartTime(request.getTime());
            if (request.getDuration() != null) {
                meeting.setEndTime(request.getTime().plusMinutes(request.getDuration()));
            }
        }
        meetingMapper.updateById(meeting);
    }

    @Override
    public void delete(Long id) {
        meetingMapper.deleteById(id);
    }

    private List<MeetingDetailResponse> convertToResponse(List<Meeting> meetings) {
        if (meetings.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> organizerIds = meetings.stream()
                .map(Meeting::getOrganizerId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        final Map<Long, String> organizerMap;
        if (!organizerIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(organizerIds);
            organizerMap = users.stream()
                    .collect(Collectors.toMap(
                            User::getId,
                            u -> u.getRealName() != null ? u.getRealName() : u.getUsername(),
                            (a, b) -> a
                    ));
        } else {
            organizerMap = Collections.emptyMap();
        }

        return meetings.stream().map(meeting -> {
            MeetingDetailResponse dto = new MeetingDetailResponse();
            dto.setId(meeting.getId());
            dto.setTitle(meeting.getTitle());
            dto.setType(meeting.getType());
            dto.setStatus(meeting.getStatus());
            dto.setDuration(meeting.getDuration());
            dto.setLocation(meeting.getLocation());
            dto.setParticipants(Collections.emptyList());

            String organizerName = organizerMap.getOrDefault(meeting.getOrganizerId(), "");
            dto.setOrganizer(organizerName);

            if (meeting.getStartTime() != null && meeting.getEndTime() != null) {
                String timeStr = meeting.getStartTime().format(TIME_FORMATTER)
                        + " - "
                        + meeting.getEndTime().format(TIME_FORMATTER);
                dto.setTime(timeStr);
            } else if (meeting.getStartTime() != null) {
                dto.setTime(meeting.getStartTime().format(TIME_FORMATTER));
            } else {
                dto.setTime("");
            }

            return dto;
        }).collect(Collectors.toList());
    }
}
