package org.haedal.zzansuni.infrastructure.challengegroup.userchallenge;

import java.time.LocalDate;

public interface DayCountType {
    LocalDate getDate();
    Integer getCount();
}
