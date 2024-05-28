package org.haedal.zzansuni.global.api;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * 모든 요청에 UUID를 생성하여 MDC에 저장하는 인터셉터
 * 로그에서 요청을 추적하고 싶을때, MDC를 통한 UUID를 사용하면 편리하다.
 */
@Slf4j
@Component
public class CommonHttpRequestInterceptor implements HandlerInterceptor {

    public static final String HEADER_REQUEST_UUID_KEY = "x-request-id";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestEventId = request.getHeader(HEADER_REQUEST_UUID_KEY);
        if (StringUtils.isEmpty(requestEventId)) {
            requestEventId = UUID.randomUUID().toString();
        }

        MDC.put(HEADER_REQUEST_UUID_KEY, requestEventId);
        return true;
    }
}
