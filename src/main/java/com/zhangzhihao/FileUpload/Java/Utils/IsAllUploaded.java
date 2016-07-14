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

    /**
     * @param md5
     * @param chunks
     * @return
     */
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
     * @param guid        随机生成的文件名
     * @param chunk       //文件分块序号
     * @param chunks      //文件分块数
     * @param fileName
     * @param ext         //文件后缀名
     * @param fileService
     */
    public static void Uploaded(String md5, String guid, String chunk, String chunks, String uploadFolderPath, String fileName, String ext, FileService fileService) throws Exception {
        if (uploadInfoList == null) {
            uploadInfoList = new ArrayList<>();
        }
        uploadInfoList.add(new UploadInfo(md5, chunks, chunk, uploadFolderPath, fileName, ext));
        boolean allUploaded = isAllUploaded(md5, chunks);
        int chunksNumber = Integer.parseInt(chunks);

        if (allUploaded) {
            mergeFile(chunksNumber, ext, guid, uploadFolderPath);
            fileService.save(new com.zhangzhihao.FileUpload.Java.Model.File(guid + ext, md5, new Date()));
        }
    }
}



