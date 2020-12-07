package com.pixeon.healthcare.config.validacao;

import lombok.Data;

@Data
public class ErrorDto {
    private final String field;

    private final String error;
}