package org.haedal.zzansuni.domain.challengegroup;

import lombok.Builder;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel;
import org.haedal.zzansuni.domain.challengegroup.image.ChallengeGroupImage;
import org.haedal.zzansuni.domain.challengegroup.userexp.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.user.UserModel;

import java.time.LocalDate;
import java.util.List;

public class ChallengeGroupModel {

    public record Main(
            Long id,
            ChallengeCategory category,
            String title,
            String content,
            Integer cumulativeCount
    ) {
        public static Main from(ChallengeGroup challengeGroup) {
            return new Main(
                    challengeGroup.getId(),
                    challengeGroup.getCategory(),
                    challengeGroup.getTitle(),
                    challengeGroup.getContent(),
                    challengeGroup.getCumulativeCount()
            );
        }
    }

    @Builder
    public record Info(
            Long id,
            ChallengeCategory category,
            String title,
            String content,
            String guide,
            Integer cumulativeCount,
            List<ChallengeModel.Main> challenges
    ) {
        public LocalDate getMinStartDate() {
            return challenges.stream()
                    .map(ChallengeModel.Main::startDate)
                    .min(LocalDate::compareTo)
                    .orElse(LocalDate.now());
        }

        public LocalDate getMaxEndDate() {
            return challenges.stream()
                    .map(ChallengeModel.Main::endDate)
                    .max(LocalDate::compareTo)
                    .orElse(LocalDate.now());
        }

        public static Info from(ChallengeGroup challengeGroup) {
            var challenges = challengeGroup.getChallenges().stream()
                    .map(ChallengeModel.Main::from)
                    .toList();
            return Info.builder()
                    .id(challengeGroup.getId())
                    .category(challengeGroup.getCategory())
                    .title(challengeGroup.getTitle())
                    .content(challengeGroup.getContent())
                    .guide(challengeGroup.getGuide())
                    .cumulativeCount(challengeGroup.getCumulativeCount())
                    .challenges(challenges)
                    .build();
        }
    }

    @Builder
    public record Detail(
            Long id,
            ChallengeCategory category,
            String title,
            String content,
            String guide,
            Integer cumulativeCount,
            List<String> imageUrls,
            List<ChallengeModel.Main> challenges
    ) {
        public LocalDate getMinStartDate() {
            return challenges.stream()
                    .map(ChallengeModel.Main::startDate)
                    .min(LocalDate::compareTo)
                    .orElse(LocalDate.now());
        }

        public LocalDate getMaxEndDate() {
            return challenges.stream()
                    .map(ChallengeModel.Main::endDate)
                    .max(LocalDate::compareTo)
                    .orElse(LocalDate.now());
        }

        public static Detail from(ChallengeGroup challengeGroup, List<ChallengeGroupImage> challengeGroupImages) {
            var challenges = challengeGroup.getChallenges().stream()
                    .map(ChallengeModel.Main::from)
                    .toList();
            var imageUrls = challengeGroupImages.stream()
                    .map(ChallengeGroupImage::getImageUrl)
                    .toList();
            return Detail.builder()
                    .id(challengeGroup.getId())
                    .category(challengeGroup.getCategory())
                    .title(challengeGroup.getTitle())
                    .content(challengeGroup.getContent())
                    .guide(challengeGroup.getGuide())
                    .cumulativeCount(challengeGroup.getCumulativeCount())
                    .imageUrls(imageUrls)
                    .challenges(challenges)
                    .build();
        }
    }

    @Builder
    public record Ranking(
            UserModel.Main user,
            Integer rank,
            Integer accumulatedPoint
    ) {
        public static Ranking from(ChallengeGroupUserExp challengeGroupUserExp, int rank) {
            return Ranking.builder()
                    .user(UserModel.Main.from(challengeGroupUserExp.getUser()))
                    .rank(rank)
                    .accumulatedPoint(challengeGroupUserExp.getTotalExp())
                    .build();
        }
    }

}
