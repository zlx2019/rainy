package com.zero.rainy.core.handler;

import com.zero.rainy.core.enums.supers.ResponseCodes;
import com.zero.rainy.core.exception.BusinessException;
import com.zero.rainy.core.exception.RecordNotFoundException;
import com.zero.rainy.core.exception.RequestLimitException;
import com.zero.rainy.core.model.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 全局异常捕获处理
 *
 * @author Zero.
 * <p> Created on 2024/9/16 02:54 </p>
 */
@Slf4j
@ResponseBody
public class DefaultExceptionAdvice {

    /**
     * 顶级异常统一处理
     * @param e         异常
     * @param request   产生异常的请求
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> exceptionHandler (Exception e, HttpServletRequest request){
        log.error("系统未知异常: {}", e.getMessage(), e);
        return exceptionHandler(ResponseCodes.Unknown);
    }

    /**
     * 系统业务异常处理
     * @param e         业务异常
     * @param request   产生异常的请求
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> businessExceptionHandler(BusinessException e, HttpServletRequest request, HttpServletResponse response) {
        if (e.getCode() != null && e.getCode().httpStatus() != null) {
            response.setStatus(e.getCode().httpStatus().value());
        }
        log.error("系统业务异常: {} - ", e.getMessage(), e);
        return Optional.ofNullable(e.getCode())
                .map(Result::fail)
                .orElseGet(()-> Result.fail(ResponseCodes.Business.getCode()));
    }

    /**
     * 资源找不到异常
     * @param e 找不到资源异常信息
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<?> noResourceFoundExceptionHandler(NoResourceFoundException e) {
        log.error("Resource Not Found [{}] - [{}]", e.getHttpMethod(), e.getResourcePath());
        return exceptionHandler(ResponseCodes.RESOURCE_NOT_FOUND);
    }

    /**
     * 数据表记录未找到
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public Result<?> recordNotFoundExceptionHandler(RecordNotFoundException e) {
        String message = StringUtils.defaultIfBlank(e.getMessage(), ResponseCodes.BUSINESS_DATA_NOT_FOUND.getCode().message());
        return Result.fail(ResponseCodes.BUSINESS_DATA_NOT_FOUND.getCode().code(), message);
    }

    /**
     * 请求限流异常处理
     */
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RequestLimitException.class)
    public Result<?> requestLimitExceptionHandler(RequestLimitException e, HttpServletRequest request) {
        log.error("Too Many Requests [{}]", request.getRequestURI());
        return exceptionHandler(ResponseCodes.REQUEST_LIMIT);
    }

    /**
     * 请求 Method 不支持异常
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = e.getMethod();
        String[] supportedMethods = e.getSupportedMethods();
        log.error("[{}] 不支持 [{}] 请求方式, 允许的请求方式: {}", path, method, supportedMethods);
        return exceptionHandler(ResponseCodes.METHOD_NOT_SUPPORT);
    }

    /**
     * 参数校验错误异常
     * @param e         校验异常信息
     * @param request   请求
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fields = bindingResult.getFieldErrors();
        log.error("============== 参数校验失败 ==============");
        Map<String, String> errors = new HashMap<>();
        for (FieldError field : fields) {
            String fieldName = field.getField();
            String message = field.getDefaultMessage();
            errors.put(fieldName, message);
            log.error("======> [{}] - {}", fieldName, message);
        }
        log.error("========================================");
        return Result.fail(ResponseCodes.PARAM_NOT_VALID).setData(errors);
    }

    /**
     * 参数类型转换异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<?> httpMessageNotReadableExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("======> [{}] '{}' can not convert to [{}] 类型", e.getPropertyName(), e.getValue(), e.getParameter().getParameterType().getName());
        return Result.fail(ResponseCodes.PARAM_NOT_VALID);
    }

    private Result<?> exceptionHandler (ResponseCodes code){
        return Result.fail(code);
    }
}
