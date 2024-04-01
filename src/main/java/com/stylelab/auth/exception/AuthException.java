package com.stylelab.auth.exception;

import com.stylelab.common.exception.CommonError;
import com.stylelab.common.exception.ServiceException;

public class AuthException extends ServiceException {

    private final CommonError serviceError;

    public AuthException(CommonError serviceError) {
        super(serviceError, serviceError.getMessage());
        this.serviceError = serviceError;
    }

    public AuthException(CommonError serviceError, String message) {
        super(serviceError, message);
        this.serviceError = serviceError;
    }

    public AuthException(CommonError serviceError, Throwable cause) {
        super(serviceError, serviceError.getMessage(), cause);
        this.serviceError = serviceError;
    }

    public AuthException(CommonError serviceError, String message, Throwable cause) {
        super(serviceError, message, cause);
        this.serviceError = serviceError;
    }
}
