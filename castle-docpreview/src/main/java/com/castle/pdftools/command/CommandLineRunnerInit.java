package com.castle.pdftools.command;

import com.castle.pdftools.service.KbVideVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class CommandLineRunnerInit implements CommandLineRunner {
    @Autowired
    private KbVideVersionService kbVideVersionService;
    @Override
    public void run(String... args) throws Exception {
        kbVideVersionService.initRun();
    }
}
