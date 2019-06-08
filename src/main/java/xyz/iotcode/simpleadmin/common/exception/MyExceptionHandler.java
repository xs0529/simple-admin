package xyz.iotcode.simpleadmin.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.iotcode.simpleadmin.common.pojo.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
@ControllerAdvice
public class MyExceptionHandler {

    @Autowired
    private ErrorInfoBuilder errorInfoBuilder;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<ErrorInfo> errorHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        // 支持跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        ErrorInfo errorInfo = errorInfoBuilder.getErrorInfo(request);
        return Result.fail(errorInfo.getStatusCode(), errorInfo.getMessage(), errorInfo);
    }

}
