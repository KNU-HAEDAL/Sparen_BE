package org.haedal.zzansuni.controller.challengegroup.challenge;

import lombok.Builder;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ChallengeRes {

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


    @Builder
    public record ChallengeCurrentDto(
        Long id,
        String title,
        Integer successCount,
        Integer totalCount,

        LocalDate participationDate,
        LocalDate startDate,
        LocalDate endDate,

        ChallengeCategory category,
        Boolean reviewWritten

    ) {

    }

    @Builder
    public record ChallengeCompleteDto(
        Long id,
        String title,

        LocalDate successDate,

        ChallengeCategory category,
        Boolean reviewWritten

    ) {

    }
}
