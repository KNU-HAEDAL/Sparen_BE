package org.haedal.zzansuni.controller.challenge;

import lombok.Builder;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.domain.challenge.ChallengeCategory;
import org.haedal.zzansuni.domain.challenge.DayType;

import java.time.LocalDate;
import java.util.List;

public class ChallengeRes {


    @Builder
    public record ChallengeDto(
            Long id,
            String title,
            String content,
            Integer participantCount,
            LocalDate startDate,
            LocalDate endDate,
            ChallengeCategory category

    ) {
    }

    @Builder
    public record ChallengeDetailDto(
            Long id,
            String title,
            String content,
            Integer participantCount,
            LocalDate startDate,
            LocalDate endDate,
            ChallengeCategory category,
            /////
            List<String> imageUrl,
            List<ChallengeDifficultyDto> difficulties
    ) {
    }

    @Builder
    public record ChallengeDifficultyDto(
            Long id,
            Integer participantCount,

            Integer difficulty,
            Integer onceExp,
            Integer successExp,
            DayType dayType,
            Integer dayCount
    ) {
    }



    @Builder
    public record ChallengeReviewDto(
            Long challengeId,
            UserRes.UserDto user,
            String content,
            Integer rating
    ) {
    }


    @Builder
    public record ChallengeRankingPagingResponse(
            List<ChallengeRankingDto> data,
            Integer totalPage,
            ChallengeRankingDto myRanking //null이면 랭킹이 없는 것
    ) {
    }


    @Builder
    public record ChallengeRankingDto(
            Integer ranking,
            //획득 포인트
            Integer acquiredPoint,
            UserRes.UserDto user
    ) {
    }
}
