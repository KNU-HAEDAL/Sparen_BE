package org.haedal.zzansuni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"org.haedal.zzansuni"})
public class ZzansuniApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzansuniApplication.class, args);
    }

}
