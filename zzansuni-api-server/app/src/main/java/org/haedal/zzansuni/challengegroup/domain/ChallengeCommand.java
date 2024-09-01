package org.haedal.zzansuni.challengegroup.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.core.utils.SelfValidating;
import org.springframework.web.multipart.MultipartFile;

public class ChallengeCommand {

    @Getter
    public static class Verificate extends SelfValidating<Verificate> {

        @NotBlank(message = "내용은 필수입니다.")
        private final String content;

        @NotNull(message = "이미지는 필수입니다.")
        private final MultipartFile image;

        @Builder
        public Verificate(String content, MultipartFile image) {
            this.content = content;
            this.image = image;
            this.validateSelf();
        }

        public VerificationCreate afterUpload(String imageUrl) {
            return VerificationCreate.builder()
                .content(content)
                .imageUrl(imageUrl)
                .build();
        }
    }

    @Getter
    public static class VerificationCreate extends SelfValidating<Verificate> {

        @NotBlank(message = "내용은 필수입니다.")
        private final String content;
        private final String imageUrl;

        @Builder
        public VerificationCreate(String content, String imageUrl) {
            this.content = content;
            this.imageUrl = imageUrl;
            this.validateSelf();
        }
    }

    @Getter
    public static class ReviewCreate extends SelfValidating<ReviewCreate> {

        @NotBlank(message = "내용은 필수입니다.")
        private final String content;

        @NotNull(message = "평점은 필수입니다.")
        @Min(1)
        @Max(5)
        private final Integer rating;

        @Builder
        public ReviewCreate(String content, Integer rating) {
            this.content = content;
            this.rating = rating;
            this.validateSelf();
        }


    }

}
