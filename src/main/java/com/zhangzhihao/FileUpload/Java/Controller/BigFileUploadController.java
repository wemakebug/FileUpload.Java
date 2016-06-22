package com.zhangzhihao.FileUpload.Java.Controller;

import com.zhangzhihao.FileUpload.Java.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/BigFileUpload")
public class BigFileUploadController {
	@Autowired
	private FileService fileService;

	/**
	 * 转向操作页面
	 *
	 * @return 操作页面
	 */
	@RequestMapping(value = "/Index", method = RequestMethod.GET)
	public String Index() {
		return "BigFileUpload/Index";
	}

}
