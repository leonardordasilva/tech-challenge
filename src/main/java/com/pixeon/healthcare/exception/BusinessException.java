package com.pixeon.healthcare.exception;

import com.pixeon.healthcare.config.validacao.ErrorDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusinessException extends Exception {
    private List<ErrorDto> errors;

    public BusinessException(String key, String message) {
        super(message);

        addError(key, message);
    }

    public void addError(String key, String message) {
        if (this.errors == null) {
            this.errors = new ArrayList<ErrorDto>();
        }

        this.errors.add(new ErrorDto(key, message));
    }
}
