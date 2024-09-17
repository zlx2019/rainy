package com.zero.rainy.core.config;

import com.zero.rainy.core.enums.supers.ResultCodes;
import com.zero.rainy.core.exception.BusinessException;
import com.zero.rainy.core.pojo.Result;
import com.zero.rainy.core.pojo.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Optional;

/**
 * 全局异常捕获处理
 *
 * @author Zero.
 * <p> Created on 2024/9/16 02:54 </p>
 */
@Slf4j
@ResponseBody
public class DefaultExceptionConfigurer {

    /**
     * 顶级异常统一处理
     * @param e         异常
     * @param request   产生异常的请求
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> exceptionHandler (Exception e, HttpServletRequest request){
        log.error("系统未知异常: {}", e.getMessage(), e);
        return exceptionHandler(ResultCodes.Unknown.getCode(), request);
    }

    /**
     * 系统业务异常处理
     * @param e         业务异常
     * @param request   产生异常的请求
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> businessExceptionHandler(BusinessException e, HttpServletRequest request) {
        log.error("系统业务异常: {} - ", e.getMessage(), e);
        return Optional.ofNullable(e.getCode())
                .map(Result::fail)
                .orElseGet(()-> Result.fail(ResultCodes.Business.getCode()));
    }

    /**
     * 资源找不到异常
     * @param e 找不到资源异常信息
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<?> noResourceFoundExceptionHandler(NoResourceFoundException e) {
        log.error("Resource Not Found [{}] - [{}]", e.getHttpMethod(), e.getResourcePath());
        return Result.fail(ResultCodes.NotFound);
    }

    /**
     * 请求 Method 不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = e.getMethod();
        String[] supportedMethods = e.getSupportedMethods();
        log.error("[{}] 不支持 [{}] 请求方式, 允许的请求方式: {}", path, method, supportedMethods);
        return exceptionHandler(ResultCodes.MethodNotSupport.getCode(), null);
    }


    private Result<?> exceptionHandler (ResultCode code, HttpServletRequest request){
        return Result.fail(code);
    }
}
