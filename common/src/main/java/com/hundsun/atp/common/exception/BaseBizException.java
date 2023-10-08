package com.hundsun.atp.common.exception;

public class BaseBizException extends BaseCommonException {
  private static final long serialVersionUID = -1654620276470609163L;
  
  public BaseBizException(int errorCode) {
    super(errorCode);
    setErrorMessage(new String[] { "" });
  }
  
  public BaseBizException(int errorCode, Throwable cause) {
    super(errorCode, cause);
    setErrorMessage(new String[] { "" });
  }
  
  public BaseBizException(int errorCode, String... messages) {
    super(errorCode, messages);
  }
  
  public BaseBizException(int errorCode, Throwable cause, String... messages) {
    super(errorCode, cause, messages);
  }
  
  public BaseBizException(String errorCode) {
    super(errorCode);
    setErrorMessage(new String[] { "" });
  }
  
  public BaseBizException(String errorCode, String... messages) {
    super(errorCode, messages);
  }
  
  public BaseBizException(String errorCode, Throwable cause) {
    super(errorCode, cause);
    setErrorMessage(new String[] { "" });
  }
  
  public BaseBizException(String errorCode, Throwable cause, String... messages) {
    super(errorCode, cause, messages);
  }
}