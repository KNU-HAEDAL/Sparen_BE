package org.haedal.zzansuni.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FailedTest {

    @DisplayName("실패하는 테스트")
    @Test
    void failedTest() {
        throw new RuntimeException("some exception message...");
    }

}
