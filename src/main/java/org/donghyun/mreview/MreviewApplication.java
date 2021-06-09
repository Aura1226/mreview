package org.donghyun.mreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //이거 추가해야 시간이 나옴
public class MreviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MreviewApplication.class, args);
    }

}
