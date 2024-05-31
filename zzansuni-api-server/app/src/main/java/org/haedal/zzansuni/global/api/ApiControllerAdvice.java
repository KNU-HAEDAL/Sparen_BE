package org.haedal.zzansuni.global.api;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.core.api.ErrorCode;
import org.haedal.zzansuni.global.exception.ExternalServerConnectionException;
import org.haedal.zzansuni.global.exception.UnauthorizedException;
import org.slf4j.MDC;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice {



    /**
     * http status: 400 AND result: FAIL
     * 잘못된 요청
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> illegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.fail("BAD_REQUEST", e.getMessage());
    }

    /**
     * http status: 400 AND result: FAIL
     * 잘못된 요청
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> illegalStateException(IllegalStateException e) {
        return ApiResponse.fail("BAD_REQUEST", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> constraintViolationException(ConstraintViolationException e) {
        var errorMsg = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("잘못된 입력 값입니다.");

        return ApiResponse.fail("BAD_REQUEST", errorMsg);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errorMsg = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("잘못된 입력 값입니다.");
        return ApiResponse.fail("BAD_REQUEST", errorMsg);
    }

    /**
     * http status: 404 AND result: FAIL
     * 존재하지 않는 엔티티
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> noSuchElementException(NoSuchElementException e) {
        return ApiResponse.fail(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> noResourceFoundException(NoResourceFoundException e) {
        return ApiResponse.fail("NOT_FOUND", e.getResourcePath() + "는 존재하지 않습니다.");
    }


    /**
     * http status: 401 AND result: FAIL
     * 인증 실패
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> onUnauthorizedException(UnauthorizedException e) {
        return ApiResponse.fail("UNAUTHORIZED", e.getMessage());
    }



    /**
     * http status: 500 AND result: FAIL
     * 시스템 예외 상황. 집중 모니터링 대상
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> onException(Exception e) {
        String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.error("eventId = {} ", eventId, e);
        return ApiResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
    }

    /**
     * http status: 500 AND result: FAIL
     * 외부 서버 연결 실패
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> externalServerConnectionException(ExternalServerConnectionException e) {
        log.error("외부 서버 연결 실패", e);
        return ApiResponse.fail("EXTERNAL_SERVER_ERROR", e.getMessage());
    }




}
