package com.pixeon.healthcare.config.validacao;

import com.pixeon.healthcare.exception.BusinessException;
import com.pixeon.healthcare.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(HttpServletRequest request, BusinessException ex) {
        return ResponseEntity.badRequest().body(new GlobalErrorDto(ex));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public GlobalErrorDto handleUnprosseasableMsgException(HttpMessageNotReadableException httpMessageNotReadableException) {
        GlobalErrorDto globalErrorDto = new GlobalErrorDto();

        ErrorDto errorDto = new ErrorDto(Constants.EMPTY_FILED, Constants.UNPROCESSABLE_INPUT_DATA);

        globalErrorDto.getErrors().add(errorDto);

        return globalErrorDto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GlobalErrorDto handle(MethodArgumentNotValidException exception) {
        GlobalErrorDto globalErrorDto = new GlobalErrorDto();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorDto erro = new ErrorDto(e.getField(), mensagem);
            globalErrorDto.getErrors().add(erro);
        });

        return globalErrorDto;
    }
}