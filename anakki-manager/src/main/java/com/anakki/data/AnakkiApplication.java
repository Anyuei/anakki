package com.anakki.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = {"com.github.yeecode.dynamicdatasource", "com.anakki.data"})
@MapperScan(value = {"com.anakki.data.mapper"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AnakkiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnakkiApplication.class, args);
    }

}


