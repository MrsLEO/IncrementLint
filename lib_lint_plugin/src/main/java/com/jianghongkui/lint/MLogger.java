package com.jianghongkui.lint;


import org.apache.http.util.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类
 * <p>
 * 最新Android studio没有gradle运行日志输出
 * 保存日志到文件中
 *
 * @author hongkui.jiang
 * @Date 2019/3/12
 */
public class MLogger {

    private static StringBuilder builder;
    private static String logFilePath;

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss -");

    private MLogger() {
    }

    public static void setOutFile(String filePath) {
        logFilePath = filePath;
    }

    public static void addLog(String msg) {
        if (builder == null) {
            builder = new StringBuilder();
        }
        builder.append(format.format(new Date()));
        builder.append(msg);
        builder.append("\n");

        if (builder.length() > 100) {
            flush();
        }
    }

    static void flush() {
        if (TextUtils.isEmpty(logFilePath)) {
            return;
        }
        if (builder == null || builder.length() == 0) return;
        File file = new File(logFilePath);

        FileOutputStream outputStream = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(builder.toString().getBytes("UTF-8"));
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
