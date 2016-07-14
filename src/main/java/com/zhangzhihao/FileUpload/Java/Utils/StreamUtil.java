package com.zhangzhihao.FileUpload.Java.Utils;


import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
    public static boolean saveStreamToFile(@NotNull final InputStream inputStream,
                                           @NotNull final String filePath) throws IOException {
        boolean flag = false;
         /*创建输出流，写入数据，合并分块*/
        OutputStream outstream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outstream.write(buffer, 0, len);
                outstream.flush();
            }
        } catch (IOException e) {
            flag = false;
            throw e;
        } finally {
            outstream.close();
            inputStream.close();
            flag = true;
        }
        return flag;
    }
}
