package com.paipai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.paipai.paipai.mapper")
@EnableScheduling
public class PaipaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaipaiApplication.class, args);
    }

}
