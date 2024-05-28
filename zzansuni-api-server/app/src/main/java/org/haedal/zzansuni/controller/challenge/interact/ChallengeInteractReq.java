package org.haedal.zzansuni.controller.challenge.interact;

import org.springframework.web.multipart.MultipartFile;

public class ChallengeInteractReq {

    public record ChallengeVerificationRequest(
            String content
    ) {
    }

    public record ChallengeReviewCreateRequest(
            String content,
            Integer rating
    ) {
    }
}
