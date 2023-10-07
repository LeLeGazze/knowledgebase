package com.castle.fortress.admin.knowledge.service.impl.file;

import com.castle.fortress.admin.knowledge.service.FileContentComparison;
import com.castle.fortress.admin.utils.FortressParseUtil;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordContentComparisonImpl implements FileContentComparison {

    @Override
    public List<String> fileContentHandle(String filePath) {
        FortressParseUtil fortressParseUtil = new FortressParseUtil();
        try {
            return fortressParseUtil.parserFileList(filePath);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}