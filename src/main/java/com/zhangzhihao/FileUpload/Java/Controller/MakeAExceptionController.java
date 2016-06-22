package com.zhangzhihao.FileUpload.Java.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MakeAExceptionController {
    @RequestMapping("/MakeAException")
    public int MakeAException(){
        return 1/0;
    }
}
