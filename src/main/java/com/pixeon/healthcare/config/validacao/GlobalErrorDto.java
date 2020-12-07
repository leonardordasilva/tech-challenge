package com.pixeon.healthcare.config.validacao;

import com.pixeon.healthcare.exception.BusinessException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GlobalErrorDto {
    private List<ErrorDto> errors = new ArrayList<>();

    public GlobalErrorDto(BusinessException businessException) {
        this.errors = businessException.getErrors();
    }
}