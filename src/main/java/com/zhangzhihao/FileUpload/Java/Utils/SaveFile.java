package com.zhangzhihao.FileUpload.Java.Utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class SaveFile {
	/**
	 *
	 * @param savePath
	 * @param fileFullName
	 * @param file
	 * @return
     * @throws Exception
     */
	public static boolean saveFile(@NotNull final String savePath,
	                               @NotNull final String fileFullName,
	                               @NotNull final MultipartFile file) throws Exception {
		byte[] data = readInputStream(file.getInputStream());
		//new一个文件对象用来保存图片，默认保存当前工程根目录
		File uploadFile = new File(savePath + fileFullName);

		//判断文件夹是否存在，不存在就创建一个
		File fileDirectory = new File(savePath);
		if (!fileDirectory.exists()) {
			//noinspection ResultOfMethodCallIgnored
			fileDirectory.mkdir();
		}

		//创建输出流
		try (FileOutputStream outStream = new FileOutputStream(uploadFile)) {//写入数据
			outStream.write(data);
			outStream.flush();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return uploadFile.exists();
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		//创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		//每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		//使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		//关闭输入流
		inStream.close();
		//把outStream里的数据写入内存
		return outStream.toByteArray();
	}
}
