package com.zhangzhihao.FileUpload.Java.Controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

//@ControllerAdvice
public class HandlerExceptionController {
    @ExceptionHandler({RuntimeException.class})
    public ModelAndView HandlerMethod(Exception ex){
        ModelAndView modelAndView = new ModelAndView("/Error/Exception");
        modelAndView.addObject("MSG",ex.toString());
        Writer writer = new StringWriter();
        ex.printStackTrace(new PrintWriter(writer));
        modelAndView.addObject("detailed",writer.toString());
        return modelAndView;
    }
}
