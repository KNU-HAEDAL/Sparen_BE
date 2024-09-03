package org.haedal.zzansuni.userchallenge.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.haedal.zzansuni.challengegroup.domain.ChallengeCommand;
import org.springframework.web.multipart.MultipartFile;

public class ChallengeReq {

    public record Verification(
        String content
    ) {

        public ChallengeCommand.Verificate toCommand(MultipartFile image) {
            return ChallengeCommand.Verificate.builder()
                .content(content)
                .image(image)
                .build();
        }

    }



}
