package com.supconit.ywgl.exceptions;

import com.supconit.common.exceptions.NestedRuntimeException;

@SuppressWarnings("serial")
public class UnsupportedBusinessException extends NestedRuntimeException {

    public UnsupportedBusinessException(String msg) {
        super(msg);
    }

    public UnsupportedBusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

}