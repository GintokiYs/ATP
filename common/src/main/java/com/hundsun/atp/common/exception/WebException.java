package com.hundsun.atp.common.exception;

public class WebException extends BaseBizException {
  public WebException(int errorCode) {
    super(errorCode);
  }
  
  public WebException(int errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  
  public WebException(int errorCode, String... messages) {
    super(errorCode, messages);
  }
  
  public WebException(int errorCode, Throwable cause, String... messages) {
    super(errorCode, cause, messages);
  }
  
  public WebException(String errorCode) {
    super(errorCode);
  }
  
  public WebException(String errorCode, String... messages) {
    super(errorCode, messages);
  }
  
  public WebException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  
  public WebException(String errorCode, Throwable cause, String... messages) {
    super(errorCode, cause, messages);
  }
}