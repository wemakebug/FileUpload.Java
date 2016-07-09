package com.zhangzhihao.FileUpload.Java.Utils;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;

public class IsImag {
    public static boolean isImage(File tempFile){
        boolean flag=false;
        try {
            ImageInputStream is= ImageIO.createImageInputStream(tempFile);
            if(null==is){
                return flag;
            }
            is.close();
            flag=true;
        }catch (Exception e){

        }
        return flag;

    }
}
