package com.example.sns.domain.post.service;

import com.example.sns.domain.post.entity.Timeline;
import com.example.sns.domain.post.repository.TimelineRepository;
import com.example.sns.util.CursorRequest;
import com.example.sns.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineReadService {
    private final TimelineRepository timelineRepository;

    public PageCursor<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        List<Timeline> timelines = findAllBy(memberId, cursorRequest);
        long nextKey = timelines.stream()
            .mapToLong(Timeline::getId)
            .min()
            .orElse(CursorRequest.NONE_KEY);
        return new PageCursor(cursorRequest.next(nextKey), timelines);
    }

    private List<Timeline> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return timelineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        } else {
            return timelineRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        }
    }
}
