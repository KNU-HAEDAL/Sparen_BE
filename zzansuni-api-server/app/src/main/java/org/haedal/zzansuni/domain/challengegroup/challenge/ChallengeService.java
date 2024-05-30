package org.haedal.zzansuni.domain.challengegroup.challenge;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.user.UserReader;
import org.haedal.zzansuni.domain.user.UserStore;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final ChallengeReader challengeReader;
    private final ChallengeStore challengeStore;

    
}
