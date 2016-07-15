package com.zhangzhihao.FileUpload.Java.Utils;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("ConstantConditions")
public class IsImag {
    public static boolean isImage(File tempFile) throws IOException {
        boolean flag=false;
        ImageInputStream is= ImageIO.createImageInputStream(tempFile);
        if(null==is){
            return flag;
        }
        is.close();
        flag=true;
        return flag;

    }
}
