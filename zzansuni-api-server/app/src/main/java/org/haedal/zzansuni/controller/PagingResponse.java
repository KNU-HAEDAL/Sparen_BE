package org.haedal.zzansuni.controller;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Builder
public record PagingResponse<T>(
        Integer totalPage,
        Boolean hasNext,
        List<T> data
) {

    /**
     * [Entity]를 [Model]로 변환하여 [PagingResponse]를 생성합니다. <br> 해당 메서드를 [Entity]와 [Model]이 1:1 매핑되는 경우에
     * 사용하는 것을 권장합니다. <br> [Template Method Pattern]을 사용하여 변환 로직을 외부에서 주입받습니다.
     */
    public static <Entity, Model> PagingResponse<Model> from(
            Page<Entity> page,
            Function<Entity, Model> converter
    ) {
        return PagingResponse.<Model>builder()
                .totalPage(page.getTotalPages())
                .hasNext(page.hasNext())
                .data(page.getContent().stream().map(converter).toList())
                .build();

    }
}
