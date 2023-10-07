package com.castle.fortress.admin.knowledge.service.impl.file;
import com.castle.fortress.admin.knowledge.service.FilePreview;
import com.castle.fortress.admin.knowledge.utis.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.*;
@Service
public class FilePreviewImpl implements FilePreview {
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
                model.addAttribute("textData", Base64.encodeBase64String(result.toString().getBytes()));
                return  "txt.ftl";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
