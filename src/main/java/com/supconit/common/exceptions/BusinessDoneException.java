package com.supconit.common.exceptions;

@SuppressWarnings("serial")
public  class BusinessDoneException extends NestedRuntimeException {

    public BusinessDoneException(String msg) {
        super(msg);
    }

    public BusinessDoneException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
