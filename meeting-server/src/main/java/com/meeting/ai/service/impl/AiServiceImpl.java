package com.meeting.ai.service.impl;

import com.meeting.ai.dto.request.AICrossAnalysisRequest;
import com.meeting.ai.dto.request.AiChatRequest;
import com.meeting.ai.dto.response.AICrossAnalysisResponse;
import com.meeting.ai.dto.response.AiChatResponse;
import com.meeting.ai.entity.Meeting;
import com.meeting.ai.mapper.MeetingMapper;
import com.meeting.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final MeetingMapper meetingMapper;

    @Override
    public AiChatResponse chat(AiChatRequest request) {
        AiChatResponse response = new AiChatResponse();
        response.setSessionId(request.getSessionId());
        response.setContent("AI 回复内容占位");
        response.setDone(true);
        return response;
    }

    @Override
    public AICrossAnalysisResponse crossAnalysis(AICrossAnalysisRequest request) {
        if (CollectionUtils.isEmpty(request.getMeetingIds())) {
            throw new IllegalArgumentException("会议ID列表不能为空");
        }

        List<Meeting> meetings = meetingMapper.selectBatchIds(request.getMeetingIds());
        List<String> titles = meetings.stream()
                .map(Meeting::getTitle)
                .collect(Collectors.toList());

        // TODO: 接入 FastGPT 真实调用，当前为模拟实现
        String content = buildMockContent(titles, request.getQuestion());

        AICrossAnalysisResponse response = new AICrossAnalysisResponse();
        response.setSessionId(UUID.randomUUID().toString().replace("-", ""));
        response.setContent(content);
        response.setInsights(meetings.stream().map(m -> {
            AICrossAnalysisResponse.Insight insight = new AICrossAnalysisResponse.Insight();
            insight.setMeetingId(m.getId());
            insight.setMeetingTitle(m.getTitle());
            insight.setKeyPoints(List.of("关键要点占位"));
            return insight;
        }).collect(Collectors.toList()));

        return response;
    }

    private String buildMockContent(List<String> titles, String question) {
        String titleText = titles.isEmpty() ? "未找到相关会议" : String.join("、", titles);
        return String.format(
                "基于您选择的会议（%s）和问题\"%s\"，AI分析结果：\n\n" +
                "1. 这些会议围绕技术架构和安全主题展开\n" +
                "2. 共识别出多个关键决策点\n" +
                "3. 发现跨会议的共性议题\n" +
                "4. 建议后续重点关注相关领域的推进",
                titleText, question
        );
    }
}
