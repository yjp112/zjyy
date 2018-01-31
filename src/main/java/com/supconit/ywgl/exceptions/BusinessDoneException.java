package com.supconit.ywgl.exceptions;

import com.supconit.common.exceptions.NestedRuntimeException;

@SuppressWarnings("serial")
public  class BusinessDoneException extends NestedRuntimeException {

    public BusinessDoneException(String msg) {
        super(msg);
    }

    public BusinessDoneException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
