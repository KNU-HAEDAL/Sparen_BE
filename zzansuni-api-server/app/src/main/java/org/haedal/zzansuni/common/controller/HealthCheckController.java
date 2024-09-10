package org.haedal.zzansuni.common.controller;

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

    @GetMapping("/api/health")
    public String healthCheck() {
        String hostname = env.getProperty("HOSTNAME");
        return "ok:" + Arrays.toString(env.getActiveProfiles())+":"+hostname;
    }

}
