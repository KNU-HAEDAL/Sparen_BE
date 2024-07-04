package org.haedal.zzansuni.controller.challengegroup;

import lombok.Builder;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupModel;
import org.haedal.zzansuni.domain.challengegroup.DayType;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public class ChallengeGroupRes {


    @Builder
    public record Info(
        Long id,
        String title,
        String content,
        Integer participantCount,
        LocalDate startDate,
        LocalDate endDate,
        ChallengeCategory category

    ) {
        public static Info from(
            ChallengeGroupModel.Detail challengeGroupDetail
        ){
            return Info.builder()
                    .id(challengeGroupDetail.getId())
                    .title(challengeGroupDetail.getTitle())
                    .content(challengeGroupDetail.getContent())
                    .participantCount(challengeGroupDetail.getCumulativeCount())
                    .startDate(challengeGroupDetail.getMinStartDate())
                    .endDate(challengeGroupDetail.getMaxEndDate())
                    .category(challengeGroupDetail.getCategory())
                    .build();
        }
        public static Info from(
                ChallengeGroupModel.Info challengeGroupInfo
        ){
            return Info.builder()
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
    public record Detail(
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
        List<Challenge> challenges
    ) {
        public static Detail from(
                ChallengeGroupModel.Detail challengeGroupDetail
        ){
            List<ChallengeModel> challenges = challengeGroupDetail
                .getChallenges();

            Integer maxDifficulty = challenges.stream()
                .map(ChallengeModel::getDifficulty)
                .max(Integer::compareTo)
                .orElse(0);


            return Detail.builder()
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
                    .map(Challenge::from)
                    .toList())
                .build();
        }

    }

    @Builder
    public record Challenge(
        Long id,
        Integer participantCount,

        Integer difficulty,
        Integer onceExp,
        Integer successExp,
        DayType dayType,
        Integer dayCount
    ) {
        public static Challenge from(
            ChallengeModel challenge
        ){
            return Challenge.builder()
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
    public record RankingPagingResponse(
            List<ChallengeGroupRes.Ranking> data,
            Integer totalPage,
            ChallengeGroupRes.Ranking myRanking //null이면 랭킹이 없는 것
    ) {
        public static RankingPagingResponse from(
                Page<ChallengeGroupModel.Ranking> rankingPage,
                ChallengeGroupModel.Ranking myRanking
        ){
            var data = rankingPage.getContent().stream()
                .map(ChallengeGroupRes.Ranking::from)
                .toList();
            return RankingPagingResponse.builder()
                    .data(data)
                    .totalPage(rankingPage.getTotalPages())
                    .myRanking(ChallengeGroupRes.Ranking.from(myRanking))
                    .build();
        }

    }


    @Builder
    public record Ranking(
        Integer ranking,
        //획득 포인트
        Integer acquiredPoint,
        UserRes.User user
    ) {
        public static Ranking from(
                ChallengeGroupModel.Ranking model
        ){
            var user = UserRes.User.from(model.getUser());
            return Ranking.builder()
                    .ranking(model.getRank())
                    .acquiredPoint(model.getAccumulatedPoint())
                    .user(user)
                    .build();
        }

    }


}
