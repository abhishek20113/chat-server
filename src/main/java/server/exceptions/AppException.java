package server.exceptions;

public class AppException extends RuntimeException {

  private ErrorCode errorCode;

  public AppException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorCode errorCode() {
    return errorCode;
  }
}
