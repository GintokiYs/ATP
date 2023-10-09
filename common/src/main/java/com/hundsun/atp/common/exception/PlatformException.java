package com.hundsun.atp.common.exception;

public class PlatformException extends BaseBizException {
    public PlatformException(int errorCode) {
        super(errorCode);
    }

    public PlatformException(int errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public PlatformException(int errorCode, String... messages) {
        super(errorCode, messages);
    }

    public PlatformException(int errorCode, Throwable cause, String... messages) {
        super(errorCode, cause, messages);
    }

    public PlatformException(String errorCode) {
        super(errorCode);
    }

    public PlatformException(String errorCode, String... messages) {
        super(errorCode, messages);
    }

    public PlatformException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public PlatformException(String errorCode, Throwable cause, String... messages) {
        super(errorCode, cause, messages);
    }
}
