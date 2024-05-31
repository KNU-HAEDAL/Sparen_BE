package org.haedal.zzansuni.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@Slf4j
public class HealthCheckController {
    private final Environment env;

    @GetMapping("/health")
    public String healthCheck() {
        return "ok" + Arrays.toString(env.getActiveProfiles());
    }

}
