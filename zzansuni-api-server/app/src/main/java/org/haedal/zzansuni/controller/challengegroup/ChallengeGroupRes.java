package org.haedal.zzansuni.controller.challengegroup;

import lombok.Builder;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.DayType;

import java.time.LocalDate;
import java.util.List;

public class ChallengeGroupRes {


    @Builder
    public record ChallengeGroupDto(
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
    public record ChallengeGroupDetailDto(
        Long id,
        String title,
        String content,
        Integer participantCount,
        LocalDate startDate,
        LocalDate endDate,
        ChallengeCategory category,
        /////
        String guide,
        Integer maxDifficulty,
        List<String> imageUrls,
        List<ChallengeDto> challenges
    ) {

    }

    @Builder
    public record ChallengeDto(
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
    public record ChallengeGroupRankingPagingResponse(
        List<ChallengeGroupRankingDto> data,
        Integer totalPage,
        ChallengeGroupRankingDto myRanking //null이면 랭킹이 없는 것
    ) {

    }


    @Builder
    public record ChallengeGroupRankingDto(
        Integer ranking,
        //획득 포인트
        Integer acquiredPoint,
        UserRes.UserDto user
    ) {

    }


}
