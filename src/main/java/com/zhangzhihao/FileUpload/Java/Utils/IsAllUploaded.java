package com.zhangzhihao.FileUpload.Java.Utils;


import com.zhangzhihao.FileUpload.Java.Model.UploadInfo;
import com.zhangzhihao.FileUpload.Java.Service.FileService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.zhangzhihao.FileUpload.Java.Utils.MergeFile.mergeFile;

public class IsAllUploaded {

    private static List<UploadInfo> uploadInfoList;

    public static boolean isAllUploaded(String md5, String chunks) {
        int size = uploadInfoList.stream()
                .filter(item -> item.getMd5().equals(md5))
                .distinct()
                .collect(Collectors.toList())
                .size();
        boolean bool = (size == Integer.parseInt(chunks));
        if (bool) {
            uploadInfoList.removeIf(item -> item.getMd5() == md5);
        }
        return bool;
    }

    /**
     * @param md5
     * @param chunk
     * @param chunks
     * @param path
     * @param fileName
     * @param ext
     */
    public static void Uploaded(String md5, String guid, String chunk, String chunks, String path, String fileName, String ext, FileService fileService) {
        if (uploadInfoList == null) {
            uploadInfoList = new ArrayList<>();
        }
        uploadInfoList.add(new UploadInfo(md5, chunks, chunk, path, fileName, ext));
        boolean allUploaded = isAllUploaded(md5, chunks);
        int chunksNumber = Integer.parseInt(chunks);

        int index = path.indexOf("build");
        String tempPath = "/src/main/webapp/upload/";
        String realPath = path.substring(0, index) + tempPath;

        String newTempPath = tempPath + guid + "/";        //创建临时文件夹保存分块文件
        String newRealPath = path.substring(0, index) + newTempPath;    //分块文件临时保存路径


        if (allUploaded) {
            try {
                mergeFile(chunksNumber, ext, md5, guid, newRealPath, realPath);
                fileService.save(new com.zhangzhihao.FileUpload.Java.Model.File(guid + ext, md5, new Date()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



