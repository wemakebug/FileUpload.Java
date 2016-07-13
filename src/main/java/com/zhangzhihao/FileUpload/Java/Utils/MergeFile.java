package com.zhangzhihao.FileUpload.Java.Utils;

import java.io.*;


public class MergeFile {

    public static void mergeFile(int chunksNumber, String ext, String md5,String guid, String newRealPath, String realPath) throws Exception {
        /*合并输入流*/
        SequenceInputStream s = null;
        InputStream s1 = new FileInputStream(newRealPath + 0 + ext);
        InputStream s2 = new FileInputStream(newRealPath + 1 + ext);
        s = new SequenceInputStream(s1, s2);
        for (int i = 2; i < chunksNumber; i++) {
            InputStream s3 = new FileInputStream(newRealPath + i + ext);
            s = new SequenceInputStream(s, s3);
        }

        /*创建输出流，写入数据，合并分块*/
        OutputStream outstream = new FileOutputStream(realPath + guid + ext);
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = s.read(buffer)) != -1) {
                outstream.write(buffer, 0, len);
                outstream.flush();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            outstream.close();
            s.close();
        }
        /*删除临时文件夹*/
        File dir= new File(newRealPath);
        File[] files=dir.listFiles();
        for (int i = 0; i <files.length ; i++) {
            try {
                files[i].delete();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        dir.delete();

    }
}
