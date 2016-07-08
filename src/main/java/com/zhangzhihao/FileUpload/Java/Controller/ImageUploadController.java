package com.zhangzhihao.FileUpload.Java.Controller;

import com.sun.javafx.css.CssError;
import com.zhangzhihao.FileUpload.Java.Model.File;
import com.zhangzhihao.FileUpload.Java.Service.FileService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

//import static com.sun.rowset.JdbcRowSetResourceBundle.fileName;
import static com.zhangzhihao.FileUpload.Java.Utils.CreateMd5.createMd5;
import static com.zhangzhihao.FileUpload.Java.Utils.DeepCopy.deepClone;
import static com.zhangzhihao.FileUpload.Java.Utils.IsImag.isImage;
import static com.zhangzhihao.FileUpload.Java.Utils.SaveFile.saveFile;


/**
 * Created by LY on 2016/6/26.
 */
@Controller
@RequestMapping("/ImageUpload")
public class ImageUploadController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/Index", method = RequestMethod.GET)
    public String Upload() {
        return "ImageUpload/Upload";
    }

    @ResponseBody
    @RequestMapping(value = "/ImageUp", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("id") String id,
                             @RequestParam("name") String name,
                             @RequestParam("type") String type,
                             @RequestParam("lastModifiedDate") String lastModifiedDate,
                             @RequestParam("size") int size,
                             @RequestParam("file") MultipartFile file) {
        String fileName = "";

        MultipartFile saveFile=null;

        try {
            saveFile= (MultipartFile) deepClone(file);
        } catch (Exception e) {
            e.printStackTrace();
        }


        java.io.File tempFile=new java.io.File(UUID.randomUUID().toString());
        try {
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }



        if(!isImage(tempFile))
            return "{\"error\":true}";



        try {
            String path = ImageUploadController.class.getResource("/").getFile();
            int build = path.indexOf("build");
            String realpath = path.substring(0, build)+"src/main/webapp/upload/";
            String ext = name.substring(name.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + ext;
            saveFile(realpath, fileName, saveFile);
        } catch (Exception ex) {
            System.out.println(ex);
            return "{\"error\":true}";
        }
        try {
            fileService.save(new File(fileName, createMd5(file).toString(), new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return"{jsonrpc = \"2.0\",id = id,filePath = \"/Upload/\" + fileFullName}";}
}

