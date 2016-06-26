package com.zhangzhihao.FileUpload.Java.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class SaveFile {
    public static boolean saveFile(String localPath, String fileFullName, MultipartFile file) throws IOException {
        try {
            InputStream inputStream = file.getInputStream();
            int available = inputStream.available();
            byte[] inOutb = new byte[available];
            int read = inputStream.read(inOutb);
            File outFile = new File(localPath + "/src/main/webapp/upload/" + fileFullName);
            FileOutputStream outStream = new FileOutputStream(outFile);
            outStream.write(read);
            inputStream.close();
            outStream.close();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
