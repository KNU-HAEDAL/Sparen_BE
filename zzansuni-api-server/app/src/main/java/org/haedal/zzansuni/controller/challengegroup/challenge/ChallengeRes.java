package org.haedal.zzansuni.controller.challengegroup.challenge;

import lombok.Builder;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel.ChallengeRecord;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationModel;

public class ChallengeRes {

    @Builder
    public record ChallengeVerificationResponse(
        Integer totalCount,
        Integer successCount,
        Integer obtainExp
    ) {

        public static ChallengeVerificationResponse from(
            ChallengeModel.ChallengeVerificationResult result) {
            return ChallengeVerificationResponse.builder()
                .totalCount(result.totalCount())
                .successCount(result.successCount())
                .obtainExp(result.obtainExp())
                .build();
        }
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

        public static ChallengeRecordResponse from(ChallengeRecord dto) {
            return ChallengeRecordResponse.builder()
                .title(dto.title())
                .totalCount(dto.totalCount())
                .successCount(dto.successCount())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .recordIds(dto.recordIds())
                .build();
        }
    }

    @Builder
    public record ChallengeRecordDetailDto(
        Long id,
        LocalDateTime createdAt,
        String content,
        String imageUrl
    ) {

        public static ChallengeRecordDetailDto from(ChallengeVerificationModel model) {
            return ChallengeRecordDetailDto.builder()
                .id(model.getId())
                .createdAt(model.getCreatedAt())
                .content(model.getContent())
                .imageUrl(model.getImageUrl())
                .build();

        }

    }


    @Builder
    public record ChallengeCurrentResponse(
        Long challengeId,
        String title,
        Integer successCount,
        Integer totalCount,

        LocalDateTime participationDate,
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
