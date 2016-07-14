package com.zhangzhihao.FileUpload.Java.Utils;


import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DeleteFolder {
    public static boolean deleteFolder(@NotNull final String folderPath) {
        /*删除临时文件夹*/
        File dir = new File(folderPath);
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
                files[i].delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dir.delete();
    }
}
