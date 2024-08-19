package com.kcc.restfulservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// 에러를 핸들링하기 위해 스프링부트에 제공
// 모든 컨트롤러가 실행 될때 @ControllerAdvice 예외를 전담해서 처리 - AOP의 종류 중 하나.
@ControllerAdvice
// 나만의 ResponseEntityExceptionHandler 만들수 있음.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    //모든 예외처리를 해주는 메서드
    @ExceptionHandler(Exception.class)
    // Exception ex - 발생한 예외 객체, WebRequest request - 현재 HTTP 요청에 대한 정보를 담고 있는 객체
    public final ResponseEntity handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse( new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // UserNotFound이라는 특정 예외를 처리 해주는 메서드 - 이 예외 처리가 아닐때는 handleAllException가 실행 됨.
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity handleUserNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse( new Date(), ex.getMessage(), request.getDescription(false));
        // HttpStatus.NOT_FOUND - HTTP 상태 코드 4040를 설정
        // ExceptionResponse 객체를 담은 ResponseEntity를 반환
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        ExceptionResponse ExceptionResponse =
//                new ExceptionResponse( new Date(), ex.getMessage(),
//                        ex.getBindingResult().toString());

        ExceptionResponse exceptionResponse =
                new ExceptionResponse( new Date(), "Validation Fail",
                        ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
