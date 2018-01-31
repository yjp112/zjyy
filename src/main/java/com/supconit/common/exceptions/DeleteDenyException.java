package com.supconit.common.exceptions;
@SuppressWarnings("serial")
public class DeleteDenyException extends BusinessDoneException {

    public DeleteDenyException(String msg) {
        super(msg);
    }
    public DeleteDenyException(String msg,Throwable cause) {
        super(msg);
    }
}
