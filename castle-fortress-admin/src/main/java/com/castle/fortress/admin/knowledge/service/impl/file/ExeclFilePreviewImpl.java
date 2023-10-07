package com.castle.fortress.admin.knowledge.service.impl.file;

import com.castle.fortress.admin.knowledge.service.FilePreview;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@Service
public class ExeclFilePreviewImpl implements FilePreview {
    @Value("${server.port}")
    String port;
    @Value("${server.servlet.context-path}")
    String path;

    @Override
    public String filePreviewHandle(Model model, String filePath) {
        if (path != null && path.equals("/")) path = "";
        String FileUrl = "/knowledge/kbVideVersion/filePreviewExecl?filePath=" + Base64.encodeBase64String(filePath.getBytes());
//        model.addAttribute("finalUrl", FileUrl);
        return FileUrl;
    }
}
