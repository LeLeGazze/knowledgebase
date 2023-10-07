package com.castle.fortress.admin.check.utils;

import java.io.File;

public class HtmlToPDFUtil {


    //    wkhtmltopdf在系统中的路径
    private static final String toPdfTool = "D:\\app\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";

    /**
     * html转pdf
     *
     * @param srcPath  html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath, String toPdfTool) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder cmd = new StringBuilder();
        cmd.append(toPdfTool);
        cmd.append(" ");
        cmd.append(" --margin-top 3cm ");//设置页面上边距 (default 10mm)
        cmd.append(" --header-spacing 5 ");// (设置页眉和内容的距离,默认0)
        cmd.append(" --footer-spacing 5 ");// (设置页脚和内容的距离)
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);
        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }
}
