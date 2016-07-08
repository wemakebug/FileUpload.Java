package com.zhangzhihao.FileUpload.Java.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by LY on 2016/7/7.
 */
public class DeepCopy {
    /**
     * 深拷贝
     *
     * @return 深拷贝得到的新实例
     */
    public static Object deepClone(Object object) throws Exception {
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(object);

        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        return ois.readObject();
    }
}
