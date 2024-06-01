package org.haedal.zzansuni.domain.challengegroup.challenge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;

@Getter
public class ChallengeCurrentDto {

    private final Long challengeId;
    private final String title;
    private final Integer successCount;
    private final Integer totalCount;
    private final LocalDateTime participationDate;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final ChallengeCategory category;
    private final Boolean reviewWritten;

    // 생성자 추가
    public ChallengeCurrentDto(Long challengeId, String title, Integer successCount,
        Integer totalCount, LocalDateTime participationDate, LocalDate startDate, LocalDate endDate,
        ChallengeCategory category, Boolean reviewWritten) {
        this.challengeId = challengeId;
        this.title = title;
        this.successCount = successCount;
        this.totalCount = totalCount;
        this.participationDate = participationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.reviewWritten = reviewWritten;
    }
}
