package org.haedal.zzansuni.controller;

import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {
    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }

    @GetMapping("/health2")
    public ApiResponse<String> healthCheck2() {
        return ApiResponse.success("ok");
    }
}
