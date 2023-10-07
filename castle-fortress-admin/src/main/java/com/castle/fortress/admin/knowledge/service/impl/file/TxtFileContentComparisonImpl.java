package com.castle.fortress.admin.knowledge.service.impl.file;

import com.castle.fortress.admin.knowledge.service.FileContentComparison;
import com.castle.fortress.admin.knowledge.service.KbVideVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
@Service
public class TxtFileContentComparisonImpl implements FileContentComparison {

    @Override
    public List<String> fileContentHandle(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}