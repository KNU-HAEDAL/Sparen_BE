package org.haedal.zzansuni.controller;

import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;

public record PagingRequest(
        @Min(0)
        int page,
        Integer size
) {
    public PagingRequest {
        if (size == null) {
            size = 20;
        }
    }

    public Pageable toPageable() {
        return Pageable.ofSize(size).withPage(page);
    }
}
