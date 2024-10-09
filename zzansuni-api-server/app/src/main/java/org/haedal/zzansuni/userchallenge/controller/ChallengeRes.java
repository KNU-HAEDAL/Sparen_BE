package org.haedal.zzansuni.userchallenge.controller;

import lombok.Builder;
import org.haedal.zzansuni.challengegroup.domain.ChallengeCategory;
import org.haedal.zzansuni.challengegroup.domain.application.ChallengeModel;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerificationStatus;
import org.haedal.zzansuni.userchallenge.domain.application.ChallengeVerificationModel;
import org.haedal.zzansuni.userchallenge.domain.application.UserChallengeModel;

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

        public static ChallengeRecordDetailDto from(ChallengeVerificationModel.Main model) {
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
        Long challengeGroupId,
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
                .challengeGroupId(current.challengeGroupId())
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
        Long challengeGroupId,
        String title,
        LocalDate successDate,
        ChallengeCategory category,
        Boolean reviewWritten
    ) {

        public static ChallengeCompleteResponse from(UserChallengeModel.Complete complete) {
            return ChallengeCompleteResponse.builder()
                .id(complete.challengeId())
                .challengeGroupId(complete.challengeGroupId())
                .title(complete.title())
                .successDate(complete.successDate())
                .category(complete.category())
                .reviewWritten(complete.reviewWritten())
                .build();
        }
    }


    @Builder
    public record ChallengeVerification(
            Long verificationId,
            String content,
            String imageUrl,
            ChallengeVerificationStatus status,
            LocalDateTime createdAt,
            String challengeGroupTitle,
            String userNickname,
            String userImageUrl
    ) {

        public static ChallengeVerification from(ChallengeVerificationModel.Admin verification) {
            return ChallengeVerification.builder()
                    .verificationId(verification.verificationId())
                    .content(verification.content())
                    .imageUrl(verification.imageUrl())
                    .status(verification.status())
                    .createdAt(verification.createdAt())
                    .challengeGroupTitle(verification.ChallengeGroupTitle())
                    .userNickname(verification.UserNickname())
                    .userImageUrl(verification.UserImageUrl())
                    .build();
        }
    }
}
