package org.haedal.zzansuni.controller.challengegroup;

import lombok.Builder;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupModel;
import org.haedal.zzansuni.domain.challengegroup.DayType;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel;

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
        public static ChallengeGroupDto from(
            ChallengeGroupModel.Detail challengeGroupDetail
        ){
            return ChallengeGroupDto.builder()
                    .id(challengeGroupDetail.getId())
                    .title(challengeGroupDetail.getTitle())
                    .content(challengeGroupDetail.getContent())
                    .participantCount(challengeGroupDetail.getCumulativeCount())
                    .startDate(challengeGroupDetail.getMinStartDate())
                    .endDate(challengeGroupDetail.getMaxEndDate())
                    .category(challengeGroupDetail.getCategory())
                    .build();
        }
        public static ChallengeGroupDto from(
                ChallengeGroupModel.Info challengeGroupInfo
        ){
            return ChallengeGroupDto.builder()
                    .id(challengeGroupInfo.getId())
                    .title(challengeGroupInfo.getTitle())
                    .content(challengeGroupInfo.getContent())
                    .participantCount(challengeGroupInfo.getCumulativeCount())
                    .startDate(challengeGroupInfo.getMinStartDate())
                    .endDate(challengeGroupInfo.getMaxEndDate())
                    .category(challengeGroupInfo.getCategory())
                    .build();
        }

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
        public static ChallengeGroupDetailDto from(
                ChallengeGroupModel.Detail challengeGroupDetail
        ){
            List<ChallengeModel> challenges = challengeGroupDetail
                .getChallenges();

            Integer maxDifficulty = challenges.stream()
                .map(ChallengeModel::getDifficulty)
                .max(Integer::compareTo)
                .orElse(0);


            return ChallengeGroupDetailDto.builder()
                .id(challengeGroupDetail.getId())
                .title(challengeGroupDetail.getTitle())
                .content(challengeGroupDetail.getContent())
                .participantCount(challengeGroupDetail.getCumulativeCount())
                .startDate(challengeGroupDetail.getMinStartDate())
                .endDate(challengeGroupDetail.getMaxEndDate())
                .category(challengeGroupDetail.getCategory())
                .guide(challengeGroupDetail.getGuide())
                .maxDifficulty(maxDifficulty)
                .imageUrls(challengeGroupDetail.getImageUrls())
                .challenges(challenges.stream()
                    .map(ChallengeDto::from)
                    .toList())
                .build();
        }

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
        public static ChallengeDto from(
            ChallengeModel challenge
        ){
            return ChallengeDto.builder()
                .id(challenge.getId())
                .participantCount(challenge.getRequiredCount())
                .difficulty(challenge.getDifficulty())
                .onceExp(challenge.getOnceExp())
                .successExp(challenge.getSuccessExp())
                .dayType(challenge.getDayType())
                .dayCount(challenge.getRequiredCount())
                .build();
        }

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
