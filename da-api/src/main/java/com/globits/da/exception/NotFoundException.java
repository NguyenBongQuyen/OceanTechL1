package com.globits.da.exception;

import com.globits.da.validation.ErrorValidation;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotFoundException extends RuntimeException {
    private final ErrorValidation errorValidation;

    public NotFoundException(ErrorValidation errorValidation) {
        super(errorValidation.getErrorMessage());
        this.errorValidation = errorValidation;
    }

}
