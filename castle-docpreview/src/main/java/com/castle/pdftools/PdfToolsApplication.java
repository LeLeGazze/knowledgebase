package com.castle.pdftools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author hcwdc.com
 */
@SpringBootApplication(scanBasePackages ={"com.castle.pdftools"})
public class PdfToolsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PdfToolsApplication.class, args);
    }
}
