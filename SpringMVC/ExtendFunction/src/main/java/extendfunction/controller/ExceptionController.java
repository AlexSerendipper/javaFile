package extendfunction.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 @author Alex
 @create 2023-03-09-14:54
 */

// @ControllerAdvice 将当前类标识为异常处理的组件(包含了controller的功能，能够被扫描到)
@ControllerAdvice
public class ExceptionController {
    // @ExceptionHandler用于设置所标识方法处理的异常
    @ExceptionHandler(value = {ArithmeticException.class, ArrayIndexOutOfBoundsException.class})
    // ex表示当前请求处理中出现的异常对象
    public String handleException(Exception ex, Model model){
        // 将异常存储在请求域中
        model.addAttribute("ex", ex);
        return "error";
    }
}