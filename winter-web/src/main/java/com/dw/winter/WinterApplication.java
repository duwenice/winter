package com.dw.winter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author duwen
 * @date 2020/4/14
 */
@SpringBootApplication(scanBasePackages = {"com.dw.winter"})
@MapperScan("com.dw.winter.biz")
public class WinterApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinterApplication.class, args);
    }
}
