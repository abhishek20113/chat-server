package server.exceptions;

public enum ErrorCode {

  InvalidCredentials("Supplied username/password is incorrect");

  private String description;

  ErrorCode(String description) {
    this.description = description;
  }

}