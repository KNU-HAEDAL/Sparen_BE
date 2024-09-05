package org.haedal.zzansuni.common.infrastructure;

import org.haedal.zzansuni.common.domain.UuidHolder;
import org.springframework.stereotype.Component;

@Component
public class SystemUuidHolder implements UuidHolder {
    @Override
    public String random() {
        return java.util.UUID.randomUUID().toString();
    }
}
