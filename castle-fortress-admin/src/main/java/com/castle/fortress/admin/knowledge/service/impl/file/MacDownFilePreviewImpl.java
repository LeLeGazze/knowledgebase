package com.castle.fortress.admin.knowledge.service.impl.file;

import com.castle.fortress.admin.knowledge.service.FilePreview;
import com.qcloud.cos.utils.Base64;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.BreakIterator;

@Service
public class MacDownFilePreviewImpl implements FilePreview {
    @Override
    public String filePreviewHandle(Model model,String filePath) {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return "";
        } else {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line).append("\r\n");
                }
                br.close();
                return Base64.encodeAsString(result.toString().getBytes());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
