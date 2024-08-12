package org.haedal.zzansuni.controller.challenge;

import lombok.Builder;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeModel;
import org.haedal.zzansuni.domain.userchallenge.application.ChallengeVerificationModel;
import org.haedal.zzansuni.domain.userchallenge.application.UserChallengeModel;

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

        public static ChallengeRecordResponse from(UserChallengeModel.Record dto) {
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

        public static ChallengeCurrentResponse from(UserChallengeModel.Current current) {
            return ChallengeCurrentResponse.builder()
                .challengeId(current.challengeId())
                .title(current.title())
                .successCount(current.successCount())
                .totalCount(current.totalCount())
                .participationDate(current.participationDate())
                .startDate(current.startDate())
                .endDate(current.endDate())
                .category(current.category())
                .reviewWritten(current.reviewWritten())
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

        public static ChallengeCompleteResponse from(UserChallengeModel.Complete complete) {
            return ChallengeCompleteResponse.builder()
                .id(complete.challengeId())
                .title(complete.title())
                .successDate(complete.successDate())
                .category(complete.category())
                .reviewWritten(complete.reviewWritten())
                .build();
        }
    }
}
