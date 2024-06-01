package org.haedal.zzansuni.domain.challengegroup;

import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel;

import java.time.LocalDate;
import java.util.List;

public class ChallengeGroupModel {
    @Builder
    @Getter
    public static class Main {
        private Long id;
        private ChallengeCategory category;
        private String title;
        private String content;
        private Integer cumulativeCount;

        public static Main from(ChallengeGroup challengeGroup) {
            return Main.builder()
                    .id(challengeGroup.getId())
                    .category(challengeGroup.getCategory())
                    .title(challengeGroup.getTitle())
                    .content(challengeGroup.getContent())
                    .cumulativeCount(challengeGroup.getCumulativeCount())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Info {
        private Long id;
        private ChallengeCategory category;
        private String title;
        private String content;
        private String guide;
        private Integer cumulativeCount;
        private List<ChallengeModel> challenges;

        public LocalDate getMinStartDate() {
            return challenges.stream()
                    .map(ChallengeModel::getStartDate)
                    .min(LocalDate::compareTo)
                    .orElse(LocalDate.now());
        }

        public LocalDate getMaxEndDate() {
            return challenges.stream()
                    .map(ChallengeModel::getEndDate)
                    .max(LocalDate::compareTo)
                    .orElse(LocalDate.now());
        }

        public static Info from(ChallengeGroup challengeGroup) {
            var challenges = challengeGroup.getChallenges().stream()
                    .map(ChallengeModel::from)
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


    @Getter
    @Builder
    public static class Detail {
        private Long id;
        private ChallengeCategory category;
        private String title;
        private String content;
        private String guide;
        private Integer cumulativeCount;
        private List<String> imageUrls;
        private List<ChallengeModel> challenges;

        public LocalDate getMinStartDate() {
            return challenges.stream()
                .map(ChallengeModel::getStartDate)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());
        }

        public LocalDate getMaxEndDate() {
            return challenges.stream()
                .map(ChallengeModel::getEndDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());
        }

        public static Detail from(ChallengeGroup challengeGroup, List<ChallengeGroupImage> challengeGroupImages) {
            var challenges = challengeGroup.getChallenges().stream()
                    .map(ChallengeModel::from)
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

}
