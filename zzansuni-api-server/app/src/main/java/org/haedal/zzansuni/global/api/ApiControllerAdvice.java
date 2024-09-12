package org.haedal.zzansuni.global.api;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.core.api.ErrorCode;
import org.haedal.zzansuni.global.exception.ExternalServerConnectionException;
import org.haedal.zzansuni.global.exception.UnauthorizedException;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.slf4j.MDC;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.info("HttpMessageNotReadableException", ex);
        return ApiResponse.fail(ErrorCode.COMMON_INVALID_REQUEST);

    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException", ex);
        return ApiResponse.fail(ErrorCode.COMMON_INVALID_METHOD);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMissingServletRequestPart(MissingServletRequestPartException ex) {
        log.error("MissingServletRequestPartException", ex);
        return ApiResponse.fail(ErrorCode.COMMON_INVALID_REQUEST);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException", ex);
        return ApiResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMissingPathVariable(MissingPathVariableException ex) {
        log.error("MissingPathVariableException", ex);
        return ApiResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiResponse<Void> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        log.error("HttpMediaTypeNotSupportedException", ex);
        return ApiResponse.fail(ErrorCode.COMMON_INVALID_MEDIA_TYPE);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        log.error("HandlerMethodValidationException", ex);
        return ApiResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER);
    }


    /**
     * http status: 400 AND result: FAIL
     * 잘못된 요청
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> illegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        return ApiResponse.fail("BAD_REQUEST", e.getMessage());
    }

    /**
     * http status: 400 AND result: FAIL
     * 잘못된 요청
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> illegalStateException(IllegalStateException e) {
        log.error("IllegalStateException", e);
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
        log.error("ConstraintViolationException", e);
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
        log.error("MethodArgumentNotValidException", e);
        return ApiResponse.fail("BAD_REQUEST", errorMsg);
    }

    /**
     * http status: 404 AND result: FAIL
     * 존재하지 않는 엔티티
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> noSuchElementException(NoSuchElementException e) {
        log.error("NoSuchElementException", e);
        return ApiResponse.fail(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> noResourceFoundException(NoResourceFoundException e) {
        log.error("NoResourceFoundException", e);
        return ApiResponse.fail("NOT_FOUND", e.getResourcePath() + "는 존재하지 않습니다.");
    }


    /**
     * http status: 401 AND result: FAIL
     * 인증 실패
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> onUnauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException", e);
        return ApiResponse.fail("UNAUTHORIZED", e.getMessage());
    }


    /**
     * http status: 500 AND result: FAIL
     * 시스템 예외 상황. 집중 모니터링 대상
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> onException(
            Exception e,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        String userId = jwtUser == null ? "anonymous" : jwtUser.getId().toString();
        log.error("eventId = {}, userId = {} ", eventId, userId, e);
        String message = ErrorCode.COMMON_SYSTEM_ERROR.getMessage() + "(eventId: " + eventId + ")";
        return ApiResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR.name(), message);
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
