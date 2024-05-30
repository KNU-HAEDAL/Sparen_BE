package org.haedal.zzansuni.controller.challenge.interact;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ChallengeInteractRes {
    @Builder
    public record ChallengeVerificationResponse(
            Integer totalCount,
            Integer successCount,
            Integer obtainExp
    ) {
    }

    @Builder
    public record ChallengeRecordResponse(
            String title,
            Integer totalCount,
            Integer successCount,
            LocalDate startDate,
            LocalDate endDate,
            List<Long> recordIds
    ) {
    }

    @Builder
    public record ChallengeRecordDetailDto(
            Long id,
            LocalDateTime createdAt,
            String content,
            String imageUrl
    ) {
    }
}
