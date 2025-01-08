//package com.zero.rainy.core.model;
//
//import com.zero.rainy.core.constant.Constant;
//import org.springframework.http.HttpStatus;
//
///**
// * 统一响应码抽象类
// *
// * @param code    响应码，一般指业务错误码。
// * @param message 响应消息，业务错误消息。
// *
// * @author Zero.
// * <p> Created on 2024/9/16 02:17 </p>
// */
//public record ResponseCode(int code, String message, HttpStatus httpStatus) {
//    public static ResponseCode of(String message){
//        return of(Constant.FAIL, message);
//    }
//    public static ResponseCode of(int code, String message) {
//        return new ResponseCode(code, message, HttpStatus.OK);
//    }
//    public static ResponseCode of(int code, String message, HttpStatus httpStatus) {
//        return new ResponseCode(code, message, httpStatus);
//    }
//}
