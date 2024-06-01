package org.haedal.zzansuni.domain.challengegroup.challenge;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.core.utils.SelfValidating;
import org.springframework.web.multipart.MultipartFile;

public class ChallengeCommand {

    @Getter
    @Builder
    public static class Verificate extends SelfValidating<Verificate> {

        @NotBlank(message = "내용은 필수입니다.")
        private final String content;

        @NotNull(message = "이미지는 필수입니다.")
        private final MultipartFile image;

        public Verificate(String content, MultipartFile image) {
            this.content = content;
            this.image = image;
            this.validateSelf();
        }
    }

    @Getter
    @Builder
    public static class ReviewCreate extends SelfValidating<ReviewCreate> {

        @NotBlank(message = "내용은 필수입니다.")
        private final String content;

        @NotNull(message = "평점은 필수입니다.")
        private final Integer rating;

        public ReviewCreate(String content, Integer rating) {
            this.content = content;
            this.rating = rating;
            this.validateSelf();
        }
    }

}
