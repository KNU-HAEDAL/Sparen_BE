package org.haedal.zzansuni.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

@ParameterObject
public record PagingRequest(
        @Parameter(description = "페이지 번호(0부터 시작)(null이면 0)")
        Integer page,
        @Parameter(description = "페이지 크기(null이면 20)")
        Integer size
) {
    public PagingRequest {
        if (size == null) {
            size = 20;
        }
        if(page == null){
            page = 0;
        }
        if(page < 0){
            throw new IllegalArgumentException("page는 0 이상이어야 합니다.");
        }
    }

    public Pageable toPageable() {
        return Pageable.ofSize(size).withPage(page);
    }
}
