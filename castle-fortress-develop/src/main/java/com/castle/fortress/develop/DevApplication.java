package com.castle.fortress.develop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 代码生成启动类
 * @author Dawn
 */
@SpringBootApplication(scanBasePackages ={"com.castle.fortress"})
public class DevApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevApplication.class, args);
    }
}
