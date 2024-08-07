package org.haedal.zzansuni.controller.challengegroup.challenge;

import lombok.Builder;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeModel;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeModel.ChallengeComplete;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeModel.ChallengeCurrent;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeModel.ChallengeRecord;
import org.haedal.zzansuni.domain.userchallenge.application.ChallengeVerificationModel;

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

        public static ChallengeCurrentResponse from(ChallengeCurrent challengeCurrent) {
            return ChallengeCurrentResponse.builder()
                .challengeId(challengeCurrent.challengeId())
                .title(challengeCurrent.title())
                .successCount(challengeCurrent.successCount())
                .totalCount(challengeCurrent.totalCount())
                .participationDate(challengeCurrent.participationDate())
                .startDate(challengeCurrent.startDate())
                .endDate(challengeCurrent.endDate())
                .category(challengeCurrent.category())
                .reviewWritten(challengeCurrent.reviewWritten())
                .build();
        }
    }

    @Builder
    public record ChallengeCompleteResponse(
        Long id,
        String title,
        LocalDate successDate,
        ChallengeCategory category,
        Boolean reviewWritten
    ) {

        public static ChallengeCompleteResponse from(ChallengeComplete challengeComplete) {
            return ChallengeCompleteResponse.builder()
                .id(challengeComplete.challengeId())
                .title(challengeComplete.title())
                .successDate(challengeComplete.successDate())
                .category(challengeComplete.category())
                .reviewWritten(challengeComplete.reviewWritten())
                .build();
        }
    }
}
