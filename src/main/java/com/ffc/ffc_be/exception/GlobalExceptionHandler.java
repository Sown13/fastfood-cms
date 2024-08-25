package com.ffc.ffc_be.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.response.ExceptionMessageResponse;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @ExceptionHandler(NotFoundException.class)
    public <T> ResponseEntity<ResponseDto<T>> errorResponse(NotFoundException ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(StatusCodeEnum.EXCEPTION0404.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> ResponseEntity<ResponseDto<T>> httpMessageNotReadableException(HttpMessageNotReadableException ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(StatusCodeEnum.EXCEPTION0503.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public <T> ResponseEntity<ResponseDto<T>> missingServletRequestParameterException(MissingServletRequestParameterException ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(StatusCodeEnum.EXCEPTION0504.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(BadRequestException.class)
    public <T> ResponseEntity<ResponseDto<T>> badRequest(BadRequestException ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(StatusCodeEnum.EXCEPTION0400.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ResponseDto<T>> exception(Exception ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(messageSource.getMessage("exception.exception", null, locale))
                .statusCode(StatusCodeEnum.EXCEPTION.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static ResponseEntity<ResponseDto<List<ExceptionMessageResponse>>> handleValidationExceptions(MethodArgumentNotValidException ex, Locale locale) {

        List<ExceptionMessageResponse> data = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String[] fieldError = error.getCodes()[0].split("\\.");
            data.add(new ExceptionMessageResponse(fieldError[fieldError.length - 1], error.getDefaultMessage()));
        });

        String message = null;

        if (data.size() > 0) {
            message = data.get(0).getMessage();
        }

        final ResponseDto<List<ExceptionMessageResponse>> dto = ResponseDto.<List<ExceptionMessageResponse>>
                        builder()
                .success(false)
                .data(data)
                .message(message)
                .statusCode(StatusCodeEnum.EXCEPTION0400.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public static ResponseEntity<ResponseDto<List<ExceptionMessageResponse>>> handleValidationExceptions(ConstraintViolationException ex, Locale locale) {

        List<ExceptionMessageResponse> data = ex.getConstraintViolations().stream().map(cv -> {
            Path.Node lastNode = null;
            Iterator<Path.Node> interator = cv.getPropertyPath().iterator();
            while (interator.hasNext()) {
                lastNode = interator.next();
            }
            return ExceptionMessageResponse.builder().name(lastNode.getName()).message(lastNode.getName()).build();
        }).collect(Collectors.toList());

        String message = null;

        if (data.size() > 0) {
            message = data.get(0).getMessage();
        }

        final ResponseDto<List<ExceptionMessageResponse>> dto = ResponseDto.<List<ExceptionMessageResponse>>
                        builder()
                .success(false)
                .data(data)
                .message(message)
                .statusCode(StatusCodeEnum.EXCEPTION0400.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public <T> ResponseEntity<ResponseDto<T>> deniedExecuted(AccessDeniedException ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(StatusCodeEnum.EXCEPTION0505.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(UserDoesNotHavePermission.class)
    public <T> ResponseEntity<ResponseDto<T>> userDoesNotHavePermission(UserDoesNotHavePermission ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(StatusCodeEnum.EXCEPTION0505.toString())
                .build();
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public <T> ResponseEntity<ResponseDto<T>> userDoesNotHaveAuthority(UserDoesNotHavePermission ex, Locale locale) {
        final ResponseDto<T> dto = ResponseDto.<T>
                        builder()
                .success(false)
                .message(ex.getMessage())
                .statusCode(StatusCodeEnum.EXCEPTION0505.toString())
                .build();
        return ResponseEntity.ok(dto);
    }
}
