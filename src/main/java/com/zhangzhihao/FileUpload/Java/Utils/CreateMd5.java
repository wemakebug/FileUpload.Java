package com.zhangzhihao.FileUpload.Java.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CreateMd5 {
    public static StringBuilder createMd5(MultipartFile file){
        StringBuilder sb = new StringBuilder();
        try {

            MessageDigest md5=MessageDigest.getInstance("MD5");
            InputStream inputStream = file.getInputStream();
            int available = inputStream.available();
            byte[] bytes = new byte[available];
            md5.update(bytes);//执行MD5算法
            for (byte by : md5.digest())
            {
                sb.append(String.format("%02X", by));//将生成的字节MD５值转换成字符串
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }
}
